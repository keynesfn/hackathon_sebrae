package bean;

import java.util.Date;

import core.db.Bean;
import core.db.ColumnDB;
import core.db.TableDB;

@TableDB(label="Projeto", name="projeto", acronym="pro")
public class ProjetoBean extends Bean {

	@ColumnDB(name="pro_id", pk=true)
	private Integer id;

	@ColumnDB(name="usu_id")
	private UsuarioBean usuario;

	@ColumnDB(name="pro_cadastro")
	private Date cadastro;

	@ColumnDB(name="pro_descricao")
	private String descricao;

	@ColumnDB(name="pro_observacao")
	private String observacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UsuarioBean getUsuario() {
		if (usuario == null) usuario = new UsuarioBean();
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}

	public Date getCadastro() {
		return cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
