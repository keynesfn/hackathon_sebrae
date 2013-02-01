package core.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import bean.UsuarioBean;

public class UserContext {

	private UsuarioBean usuario;
	private HashMap<String, Object> map = new HashMap<String, Object>();

	public UsuarioBean getUsuario() {
		if (usuario == null) usuario = new UsuarioBean();
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}
	
	public Date getDataAtual() {
		return new Date();
	}
	public String getHoraAtual() {
		SimpleDateFormat sdf_hora = new SimpleDateFormat("HH:mm");
		return sdf_hora.format(getDataAtual());
	}
	
	public void putObject(String key, Object value) {
		map.put(key, value);
	}
	
	public Object getObject(String key) {
		return map.get(key);
	}
}
