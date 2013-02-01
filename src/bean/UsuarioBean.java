package bean;

import core.db.Bean;
import core.db.ColumnDB;
import core.db.TableDB;

@TableDB(label="Usuario", name="usuario", acronym="usu")
public class UsuarioBean extends Bean {

	@ColumnDB(name="usu_id", pk=true)
	private Integer id;

	@ColumnDB(name="usu_cadastro")
	private String cadastro;
	
	@ColumnDB(name="usu_nome")
	private String nome;

	@ColumnDB(name="usu_usuario")
	private String usuario;

	@ColumnDB(name="usu_senha")
	private String senha;

	@ColumnDB(name="usu_email")
	private String email;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCadastro() {
		return cadastro;
	}
	public void setCadastro(String cadastro) {
		this.cadastro = cadastro;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
