package core.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.exceptions.DeveloperException;
import core.exceptions.NotFoundException;


public class TransactionDB {
	private static Connection connDefault;
	private Connection conn;

	private TransactionDB() throws Exception {
		if (connDefault == null) connDefault = ConnectionDB.getConnection();
	}

	public static TransactionDB getInstance() throws Exception {
		return new TransactionDB();
	}

	public void start() throws Exception {
		conn = ConnectionDB.createConnection();
	}

	public void close() throws Exception {
		validationConnActive();
		conn.close();
		conn = null;
	}

	public void commit() throws Exception {
		validationConnActive();
		conn.commit();
	}

	public void rollback() throws Exception {
		validationConnActive();
		conn.rollback();
	}

	public void insert(Bean bean) throws DeveloperException, Exception {
		validationConnActive();
		validationNewBean(bean);

		TableDB tableDB = (TableDB) bean.getClass().getAnnotation(TableDB.class);
		Field[] fieldList = bean.getClass().getDeclaredFields();

		// agora vou fazer o insert
		StringBuilder sb = new StringBuilder();
		sb.append( "insert into " );
		sb.append( tableDB.name() );
		sb.append( " (" );

		String temp = "";
		for (Field field : fieldList) {
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;

			sb.append( temp );
			sb.append( columnDB.name() );
			temp = ", ";
		}
		sb.append( ") values (" );
		temp = "";
		for (int i = 0; i < fieldList.length; i++) {
			ColumnDB columnDB = fieldList[i].getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;

			sb.append( temp );
			sb.append( "?" );
			temp = ", ";
		}
		sb.append( ")" );

		PreparedStatement ps = conn.prepareStatement(sb.toString());
		for (int i = 0; i < fieldList.length; i++) {
			Field field = fieldList[i];

			int x = i+1;
			if (field.getType().equals(Integer.class)) {
				ps.setInt(x, (Integer)bean.getAttribute(field.getName()));

			} else if (field.getType().equals(Date.class)) {
				Date tempDate = ((Date)bean.getAttribute(field.getName()));
				if (tempDate != null) {
					ps.setDate(x, new java.sql.Date(tempDate.getTime()));
				}

			} else if (field.getType().equals(String.class)) {
				ps.setString(x, (String)bean.getAttribute(field.getName()));
			} else if (field.getType().getSuperclass().equals(Bean.class)) {
				Field[] fields = field.getType().getDeclaredFields();
				for (Field fieldBean : fields) {
					ColumnDB ann = fieldBean.getAnnotation(ColumnDB.class);
					if (ann == null) continue;
					if (ann.pk() == false) continue;

					String newName = field.getName() + "." + fieldBean.getName();
					if (fieldBean.getType().equals(Integer.class)) {
						ps.setInt(x, (Integer)bean.getAttribute(newName));

					} else if (fieldBean.getType().equals(Date.class)) {
						Date tempDate = ((Date)bean.getAttribute(newName));
						if (tempDate != null) {
							ps.setDate(x, new java.sql.Date(tempDate.getTime()));
						}
					} else if (fieldBean.getType().equals(String.class)) {
						ps.setString(x, (String)bean.getAttribute(newName));
					} else {
						throw new DeveloperException("Situacao nao prevista no banco de dados");
					}
					break;
				}
			} else {
				throw new DeveloperException("Situacao nao prevista no banco de dados");
			}
		}

		ps.executeUpdate();
		ps.close();
	}

	public void update(Bean bean) throws Exception {
		validationConnActive();
		validationNewBean(bean);

		TableDB tableDB = (TableDB) bean.getClass().getAnnotation(TableDB.class);
		Field[] fieldList = bean.getClass().getDeclaredFields();

		StringBuilder sb = new StringBuilder();
		sb.append( "update " );
		sb.append( tableDB.name() );
		sb.append( " set " );

		String temp = "";
		for (Field field : fieldList) {
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;
			if (columnDB.pk() == true) continue;

			sb.append( temp );
			sb.append( columnDB.name() );
			sb.append( " = ?" );
			temp = ", ";
		}

		sb.append(" where ");
		temp = "";
		for (Field field : fieldList) {
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;
			if (columnDB.pk() == false) continue;

			sb.append( temp );
			sb.append( columnDB.name() );
			sb.append( " = ?" );
			temp = " and ";
		}


		// value
		int x = 1;
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		for (int i = 0; i < fieldList.length; i++) {
			Field field = fieldList[i];
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;
			if (columnDB.pk() == true) continue;

			if (field.getType().equals(Integer.class)) {
				ps.setInt(x++, (Integer)bean.getAttribute(field.getName()));

			} else if (field.getType().equals(Date.class)) {
				Date tempDate = ((Date)bean.getAttribute(field.getName()));
				if (tempDate == null) tempDate = new Date();
				ps.setDate(x++, new java.sql.Date(tempDate.getTime()));
			} else if (field.getType().equals(String.class)) {
				ps.setString(x++, (String)bean.getAttribute(field.getName()));
			} else if (field.getType().getSuperclass().equals(Bean.class)) {
				Field[] fields = field.getType().getDeclaredFields();
				for (Field fieldBean : fields) {
					ColumnDB ann = fieldBean.getAnnotation(ColumnDB.class);
					if (ann == null) continue;
					if (ann.pk() == false) continue;

					String newName = field.getName() + "." + fieldBean.getName();
					if (fieldBean.getType().equals(Integer.class)) {
						ps.setInt(x++, (Integer)bean.getAttribute(newName));

					} else if (fieldBean.getType().equals(Date.class)) {
						Date tempDate = ((Date)bean.getAttribute(newName));
						if (tempDate != null) {
							ps.setDate(x++, new java.sql.Date(tempDate.getTime()));
						}
					} else if (fieldBean.getType().equals(String.class)) {
						ps.setString(x++, (String)bean.getAttribute(newName));
					} else {
						throw new DeveloperException("Situacao nao prevista no banco de dados");
					}
					break;
				}
			}
		}

		StringBuilder log = new StringBuilder();
		for (int i = 0; i < fieldList.length; i++) {
			Field field = fieldList[i];
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;
			if (columnDB.pk() == false) continue;

			if (field.getType().equals(Integer.class)) {
				Integer value = (Integer)bean.getAttribute(field.getName());
				log.append(value + ", ");
				ps.setInt(x++, value);
			} else if (field.getType().getSuperclass().equals(Bean.class)) {
				Field[] fields = field.getType().getDeclaredFields();
				for (Field fieldBean : fields) {
					ColumnDB ann = fieldBean.getAnnotation(ColumnDB.class);
					if (ann == null) continue;
					if (ann.pk() == false) continue;

					String newName = field.getName() + "." + fieldBean.getName();
					if (fieldBean.getType().equals(Integer.class)) {
						Integer value = (Integer)bean.getAttribute(newName);
						log.append(value + ", ");
						ps.setInt(x++, value);
					} else {
						throw new DeveloperException("Situacao nao prevista no banco de dados");
					}
					break;
				}
			} else {
				throw new DeveloperException("Situacao nao prevista no banco de dados");
			}
		}

		ps.executeUpdate();
		ps.close();
	}

	public void delete(Bean bean) throws Exception {
		validationConnActive();
		validationNewBean(bean);

		TableDB tableDB = (TableDB) bean.getClass().getAnnotation(TableDB.class);
		Field[] fieldList = bean.getClass().getDeclaredFields();

		StringBuilder sb = new StringBuilder();
		sb.append( "delete " );
		sb.append( " from " );
		sb.append( tableDB.name() );
		sb.append( " where " );

		String temp = "";
		for (int i = 0; i < fieldList.length; i++) {
			Field field = fieldList[i];
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;
			if (columnDB.pk() == false) continue;

			sb.append( temp );
			sb.append( columnDB.name() );
			sb.append( " = ?" );
			temp = " and ";
		}

		PreparedStatement ps = conn.prepareStatement(sb.toString());


		StringBuilder log = new StringBuilder();
		int x = 1;
		for (int i = 0; i < fieldList.length; i++) {
			Field field = fieldList[i];
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;
			if (columnDB.pk() == false) continue;

			if (field.getType().equals(Integer.class)) {
				Integer value = (Integer)bean.getAttribute(field.getName());
				log.append(value + ", ");
				ps.setInt(x++, value);
			} else if (field.getType().getSuperclass().equals(Bean.class)) {
				Field[] fields = field.getType().getDeclaredFields();
				for (Field fieldBean : fields) {
					ColumnDB ann = fieldBean.getAnnotation(ColumnDB.class);
					if (ann == null) continue;
					if (ann.pk() == false) continue;

					String newName = field.getName() + "." + fieldBean.getName();
					if (fieldBean.getType().equals(Integer.class)) {
						Integer value = (Integer)bean.getAttribute(newName);
						log.append(value + ", ");
						ps.setInt(x++, value);
					} else {
						throw new DeveloperException("Situacao nao prevista no banco de dados");
					}
					break;
				}
			} else {
				throw new DeveloperException("Situacao nao prevista no banco de dados");
			}
		}
		ps.execute();
		ps.close();
	}

	public Bean selectById(Class<? extends Bean> bean, Integer ... ids) throws NotFoundException, DeveloperException, Exception {
		TableDB tableDB = (TableDB) bean.getAnnotation(TableDB.class);
		Field[] fieldList = bean.getDeclaredFields();

		ids = addPkUniIfNecessary(fieldList, ids);
		validationPk(tableDB, fieldList, ids);

		StringBuilder sb = new StringBuilder();
		sb.append( "select " );
		String temp = "";
		for (Field field : fieldList) {
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;

			sb.append( temp );
			sb.append( columnDB.name() );
			temp = ", ";
		}
		sb.append( " from " );
		sb.append( tableDB.name() );

		sb.append( " where " );

		temp = "";
		for (int i = 0; i < fieldList.length; i++) {
			Field field = fieldList[i];
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;
			if (columnDB.pk() == false) continue;

			sb.append( temp );
			sb.append( columnDB.name() );
			sb.append( " = ?" );
			temp = " and ";
		}

		Connection c = (conn != null) ? conn : connDefault;
		PreparedStatement ps = c.prepareStatement(sb.toString());

		for (int i = 0; i < ids.length; i++) {
			ps.setInt(i+1, ids[i]);
		}


		Bean result = null;
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result = bean.newInstance();

			for (int i = 0; i < fieldList.length; i++) {
				Field field = fieldList[i];
				ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
				if (columnDB == null) continue;

				if (field.getType().equals(Integer.class)) {
					result.setAttribute(field.getName(), rs.getInt(columnDB.name()));
				} else if (field.getType().equals(Date.class)) {
					java.sql.Date x = rs.getDate(columnDB.name());
					if (x != null) {
						result.setAttribute(field.getName(), new Date(x.getTime()));
					}
				} else if (field.getType().equals(String.class)) {
					result.setAttribute(field.getName(), rs.getString(columnDB.name()));
				} else if (field.getType().getSuperclass().equals(Bean.class)) {
					Field[] fields = field.getType().getDeclaredFields();
					for (Field fieldBean : fields) {
						ColumnDB ann = fieldBean.getAnnotation(ColumnDB.class);
						if (ann == null) continue;

						String newName = field.getName() + "." + fieldBean.getName();
						if (fieldBean.getType().equals(Integer.class)) {
							result.setAttribute(newName, rs.getInt(ann.name()));
						} else if (fieldBean.getType().equals(Date.class)) {
							java.sql.Date tempDate = rs.getDate(ann.name());
							if (tempDate != null) {
								result.setAttribute(newName, new Date(tempDate.getTime()));
							}
						} else if (fieldBean.getType().equals(String.class)) {
							result.setAttribute(newName, rs.getString(ann.name()));
						} else {
							throw new DeveloperException("Situacao nao prevista no banco de dados");
						}
						break;
					}
				} else {
					throw new DeveloperException("Situacao nao prevista no banco de dados");
				}
			}
		}
		ps.close();

		if (result == null)
			throw new NotFoundException("Nenhum "+ tableDB.label().toLowerCase() +" encontrado.");

		result.setCreated();
		return result;
	}

	private Integer[] addPkUniIfNecessary(Field[] fieldList, Integer[] ids) {
		List<Integer> xxx = new ArrayList<Integer>();
		for (Field f : fieldList) {
			break;
		}
		for (Integer x : ids) {
			xxx.add(x);
		}
		return xxx.toArray(new Integer[xxx.size()]);
	}

	public List<? extends Bean> selectAll(Class<? extends Bean> bean) throws NotFoundException, Exception {
		TableDB tableDB = (TableDB) bean.getAnnotation(TableDB.class);
		Field[] fieldList = bean.getDeclaredFields();

		StringBuilder sb = new StringBuilder();
		sb.append( "select " );
		String temp = "";
		for (Field field : fieldList) {
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
			if (columnDB == null) continue;

			sb.append( temp );
			sb.append( columnDB.name() );
			temp = ", ";
		}
		sb.append( " from " );
		sb.append( tableDB.name() );

		Connection c = (conn != null) ? conn : connDefault;
		PreparedStatement ps = c.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();

		List<Bean> result = new ArrayList<Bean>();
		while (rs.next()) {
			Bean obj = bean.newInstance();

			for (int i = 0; i < fieldList.length; i++) {
				Field field = fieldList[i];
				ColumnDB columnDB = field.getAnnotation(ColumnDB.class);
				if (columnDB == null) continue;

				if (field.getType().equals(Integer.class)) {
					obj.setAttribute(field.getName(), rs.getInt(columnDB.name()));
				} else if (field.getType().equals(Date.class)) {
					java.sql.Date x = rs.getDate(columnDB.name());
					if (x != null) {
						obj.setAttribute(field.getName(), new Date(x.getTime()));
					}
				} else if (field.getType().equals(String.class)) {
					obj.setAttribute(field.getName(), rs.getString(columnDB.name()));
				} else if (field.getType().getSuperclass().equals(Bean.class)) {
					Field[] fields = field.getType().getDeclaredFields();
					for (Field fieldBean : fields) {
						ColumnDB ann = fieldBean.getAnnotation(ColumnDB.class);
						if (ann == null) continue;

						String newName = field.getName() + "." + fieldBean.getName();
						if (fieldBean.getType().equals(Integer.class)) {
							obj.setAttribute(newName, rs.getInt(ann.name()));
						} else if (fieldBean.getType().equals(Date.class)) {
							java.sql.Date tempDate = rs.getDate(ann.name());
							if (tempDate != null) {
								obj.setAttribute(newName, new Date(tempDate.getTime()));
							}
						} else if (fieldBean.getType().equals(String.class)) {
							obj.setAttribute(newName, rs.getString(ann.name()));
						} else {
							throw new DeveloperException("Situacao nao prevista no banco de dados");
						}
						break;
					}
				} else {
					throw new DeveloperException("Situacao nao prevista no banco de dados");
				}
			}
			result.add( obj );
		}
		ps.close();

		if (result.size() == 0)
			throw new NotFoundException("Nenhum "+ tableDB.label().toLowerCase() +" encontrado.");

		return result;
	}

	public Bean create(Class<? extends Bean> bean) throws Exception {
		Field[] fieldList = bean.getDeclaredFields();

		Bean obj = bean.newInstance();
		obj.setCreated();
		setIdIfNull(obj, fieldList);

		return obj;
	}


	//
	// HELPER
	private void validationPk(TableDB tableDB, Field[] fieldList, Integer ... ids) throws DeveloperException {
		int count = 0;
		for (int i = 0; i < fieldList.length; i++) {
			Field field = fieldList[i];
			ColumnDB fieldDB = field.getAnnotation(ColumnDB.class);
			if (fieldDB == null) continue;
			if (fieldDB.pk() == false) continue;

			count++;	
		}

		if (ids.length != count) 
			throw new DeveloperException("A tabela "+ tableDB.name() +" possui " + count + " colunas como PK. Você informou apenas " + ids.length +". Verifique!");
	}
	private void validationConnActive() throws DeveloperException {
		if (conn == null)
			throw new DeveloperException("Não foi chamado o metodo start do objeto transaction");
	}
	private void validationNewBean(Bean bean) throws DeveloperException {
		if (bean.isCreated() != 1)
			throw new DeveloperException("Não foi chamado o metodo CREATE para iniciar o Bean");
	}

	private void setIdIfNull(Bean bean, Field[] fieldList) throws Exception {
		for (int i = 0; i < fieldList.length; i++) {
			Field atribute = fieldList[i];
			ColumnDB columnDB = atribute.getAnnotation(ColumnDB.class);

			if (columnDB == null) continue;
			if (columnDB.pk() == false) continue;

			Object value = bean.getAttribute( atribute.getName() );
			if (value == null) {
				Integer newId = getNextId(bean);
				bean.setAttribute(atribute.getName(), newId);
			}
		}
	}
	private Integer getNextId(Bean bean) throws Exception {
		validationConnActive();

		TableDB tableDB = (TableDB) bean.getClass().getAnnotation(TableDB.class);
		Field[] fieldList = bean.getClass().getDeclaredFields();

		StringBuilder sb = new StringBuilder();
		sb.append( "select " );
		for (Field field : fieldList) {
			ColumnDB columnDB = field.getAnnotation(ColumnDB.class);

			if (columnDB == null) continue;
			if (columnDB.pk() == false) continue;
			if (columnDB.name().equalsIgnoreCase("uni_id")) continue;

			sb.append( "max(" );
			sb.append( columnDB.name() );
			sb.append( ") as id" );
		}
		sb.append( " from " );
		sb.append( tableDB.name() );

		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();

		Integer result = 0;
		while (rs.next()) {
			result = rs.getInt("id");
		}
		ps.close();
		return result + 1;
	}
}
