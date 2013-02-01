package bean;

import java.util.Date;

import core.db.Bean;
import core.db.ColumnDB;
import core.db.TableDB;

@TableDB(label="Tarefa", name="tarefa", acronym="tar")
public class TarefaBean extends Bean {

	@ColumnDB(name="tar_id", pk=true)
	private Integer id;

	@ColumnDB(name="pro_id")
	private ProjetoBean projeto;

	@ColumnDB(name="tar_cadastro")
	private Date cadastro;

	@ColumnDB(name="tar_situacao")
	private Integer situacao;

	@ColumnDB(name="tar_titulo")
	private String titulo;

	@ColumnDB(name="tar_descricao")
	private String descricao;

	@ColumnDB(name="tar_cor")
	private String cor;

	@ColumnDB(name="tar_quadro")
	private Integer quadro;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProjetoBean getProjeto() {
		if (projeto == null ) projeto = new ProjetoBean();
		return projeto;
	}

	public void setProjeto(ProjetoBean projeto) {
		this.projeto = projeto;
	}

	public Date getCadastro() {
		return cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Integer getQuadro() {
		return quadro;
	}

	public void setQuadro(Integer quadro) {
		this.quadro = quadro;
	}

}