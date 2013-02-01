package bean;

import java.util.Date;

import core.db.Bean;
import core.db.ColumnDB;
import core.db.TableDB;

@TableDB(label="TarefaComentario", name="tarefacomentario", acronym="tarc")
public class TarefaComentarioBean extends Bean {

	@ColumnDB(name="tarc_id", pk=true)
	private Integer id;

	@ColumnDB(name="tar_id")
	private TarefaBean tarefa;

	@ColumnDB(name="tarc_data")
	private Date data;

	@ColumnDB(name="tarc_hora")
	private String hora;

	@ColumnDB(name="tarc_comentario")
	private String comentario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TarefaBean getTarefa() {
		if (tarefa == null) tarefa = new TarefaBean();
		return tarefa;
	}

	public void setTarefa(TarefaBean tarefa) {
		this.tarefa = tarefa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}