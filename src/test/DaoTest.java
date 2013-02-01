package test;

import bean.TarefaBean;
import core.db.TransactionDB;

public class DaoTest {

	public static void main(String[] args) throws Exception {
		
		TransactionDB trans = TransactionDB.getInstance();
		trans.start();
		TarefaBean tBean = (TarefaBean) trans.selectById(TarefaBean.class, 2);
		tBean.setQuadro(1);
		trans.update(tBean);
		trans.commit();
		trans.close();

		
		
	}
	
}
