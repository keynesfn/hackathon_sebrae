package view;

import java.util.ArrayList;
import java.util.List;

import bean.ProjetoBean;
import bean.TarefaBean;

public class CanvasView {
	public static final String PL = "\n";
	
	public static final String WINDOW_NOVO_PROJETO = "window_novo_projeto";
	public static final String WINDOW_NOVA_TAREFA = "window_nova_tarefa";
	public static final String WINDOW_TAREFA = "window_tarefa";
	

	public static String criaHtmlNovaProjetoWindow() {
		StringBuilder sb = new StringBuilder();
		sb.append("	<div id=\""+ WINDOW_NOVO_PROJETO +"\">                                                                                              " + PL);
		sb.append("		<form class=\"ui-form ui-form\" method=\"post\" action=\"canvas\">                                                                         " + PL);
		sb.append("			<input type=\"hidden\" name=\"acao\" value=\"novoProjeto\" />                                                                   " + PL);
		sb.append("			<label class=\"default\">Descrição</label>                                                                                     " + PL);
		sb.append("			<input type=\"text\" class=\"ui-widget-content ui-corner-all\" name=\"descricao\" />                                                                   " + PL);
		sb.append("			<br /> <label class=\"default\">Observação</label>                                                                                     " + PL);
		sb.append("			<textarea name=\"observacao\"                                                                                " + PL);
		sb.append("				class=\"ui-widget-content ui-corner-all\"></textarea>                                                    " + PL);
		sb.append("			<br /> <input type=\"submit\" class=\"button\" value=\"Salvar\" />                                                  " + PL);
		sb.append("		</form>                                                                                                          " + PL);
		sb.append("	</div>                                                                                                               " + PL);

		return sb.toString();
	}
	
	public static String criaHtmlNovaTarefaWindow() {
		StringBuilder sb = new StringBuilder();

		sb.append("	<div id=\""+ WINDOW_NOVA_TAREFA +"\">                                                                                " + PL);
		sb.append("		<form class=\"ui-form\" id=\"novaTarefaFormWindow\" method=\"post\">                                             " + PL);
		sb.append("			<input type=\"hidden\" name=\"acao\" value=\"salvarTarefa\" />                                                 " + PL);
		sb.append("			<label class=\"default\">Titulo</label>                                                                      " + PL);
		sb.append("			<input id=\"field-titulo\" type=\"text\" class=\"ui-widget-content ui-corner-all\" name=\"titulo\" />        " + PL);
		sb.append("			<label class=\"default\">Descrição</label>                                                                   " + PL);
		sb.append("			<textarea id=\"field-descricao\" name=\"descricao\"                                                          " + PL);
		sb.append("				class=\"ui-widget-content ui-corner-all\"></textarea>                                                    " + PL);
		sb.append("			<br /> <label class=\"default\">Cor</label> <select id=\"field-cor\" name=\"cor\"                            " + PL);
		sb.append("				class=\"ui-widget-content ui-corner-all\">                                                               " + PL);
		sb.append("				<option value=\"00F\">Azul</option>                                                                      " + PL);
		sb.append("				<option value=\"0FF\">Ciano</option>                                                                     " + PL);
		sb.append("			</select>                                                                                                    " + PL);
		sb.append("			<br /> <label class=\"default\">Situação</label>                                                             " + PL);
		sb.append("			<select id=\"field-situacao\" name=\"situacao\"                                                              " + PL);
		sb.append("				class=\"ui-widget-content ui-corner-all\">                                                               " + PL);
		sb.append("				<option value=\"0\">Hipotese</option>                                                                    " + PL);
		sb.append("				<option value=\"1\">Funcionou</option>                                                                   " + PL);
		sb.append("				<option value=\"-1\">Não funcionou</option>                                                              " + PL);
		sb.append("			</select> <br />                                                                                             " + PL);
		sb.append("			<button id=\"salvarWindow\" class=\"button\">Salvar</button>                                                                  " + PL);
		sb.append("		</form>                                                                                                          " + PL);
		sb.append("	</div>                                                                                                               " + PL);

		return sb.toString();
	}

	public static String criaHtmlTarefaWindow() {
		StringBuilder sb = new StringBuilder();
		sb.append("	<div id=\""+ WINDOW_TAREFA +"\">                                                                                              " + PL);
		 
		sb.append("	<ul>                                                 " + PL);
		sb.append("	  <li><a href=\"#tabs-1\">Alterar</a></li>           " + PL);
		sb.append("	  <li><a href=\"#tabs-2\">Comentario</a></li>        " + PL);
		sb.append("	  <li><a href=\"#tabs-3\">Excluir</a></li>           " + PL);
		sb.append("	</ul>                                                " + PL);

		sb.append("	<div id=\"tabs-1\">	                                 " + PL);	
		sb.append("		<form class=\"ui-form\" id=\"alterarTarefaFormWindow\" method=\"post\">                                                       " + PL);
		sb.append("			<input type=\"hidden\" name=\"acao\" value=\"alterarTarefa\" />                                                 " + PL);
		sb.append("			<label class=\"default\">Titulo</label>                                                                      " + PL);
		sb.append("			<input id=\"field-titulo\" type=\"text\" class=\"ui-widget-content ui-corner-all\" name=\"titulo\" />        " + PL);
		sb.append("			<label class=\"default\">Descrição</label>                                                                   " + PL);
		sb.append("			<textarea id=\"field-descricao\" name=\"descricao\"                                                          " + PL);
		sb.append("				class=\"ui-widget-content ui-corner-all\"></textarea>                                                    " + PL);
		sb.append("			<br /> <label class=\"default\">Cor</label> <select id=\"field-cor\" name=\"cor\"                            " + PL);
		sb.append("				class=\"ui-widget-content ui-corner-all\">                                                               " + PL);
		sb.append("				<option value=\"00F\">Azul</option>                                                                      " + PL);
		sb.append("				<option value=\"0FF\">Ciano</option>                                                                     " + PL);
		sb.append("			</select>                                                                                                    " + PL);
		sb.append("			<br /> <label class=\"default\">Situação</label>                                                             " + PL);
		sb.append("			<select id=\"field-situacao\" name=\"situacao\"                                                              " + PL);
		sb.append("				class=\"ui-widget-content ui-corner-all\">                                                               " + PL);
		sb.append("				<option value=\"0\">Hipotese</option>                                                                    " + PL);
		sb.append("				<option value=\"1\">Funcionou</option>                                                                   " + PL);
		sb.append("				<option value=\"-1\">Não funcionou</option>                                                              " + PL);
		sb.append("			</select> <br />                                                                                             " + PL);
		sb.append("			<button id=\"alterarButton\" class=\"button\">Alterar</button>                                                                  " + PL);
		sb.append("		</form>                                                                                                          " + PL);
		sb.append("	</div>	                                                                                                             " + PL);
		
		
		sb.append("	<div id=\"tabs-2\">	                                                                                                  " + PL);	
		sb.append("		<div id=\"comentariosDiv\">Carregando...</div>                                                                           " + PL);
		sb.append("		<form class=\"ui-form\" id=\"comentarTarefaFormWindow\" method=\"post\">                                                       " + PL);
		sb.append("			<input type=\"hidden\" name=\"acao\" value=\"comentarTarefa\" />                                                 " + PL);
		sb.append("			<label class=\"default\">Comentario</label>                                                                  " + PL);
		sb.append("			<textarea id=\"field-comentario\" name=\"comentario\"                                                        " + PL);
		sb.append("				class=\"ui-widget-content ui-corner-all\"></textarea>                                                    " + PL);
		sb.append("			<button id=\"comentarButton\" class=\"button\">Comentar</button>                                                                  " + PL);
		sb.append("		</form>                                                                                                          " + PL);
		sb.append("	</div>	                                                                                                             " + PL);	

		sb.append("	<div id=\"tabs-3\">	                                                                                                 " + PL);	
		sb.append("	  Deseja mesmo excluir esse postIt?<br />                                                                            " + PL);	
		sb.append("	  <button id=\"excluirButton\" class=\"button\">Excluir</button>                                                                      " + PL);
		sb.append("	</div>" + PL);
		
		
		sb.append("	</div>" + PL);
		return sb.toString();
	}
	
	public static String criaHtmlHome(List<ProjetoBean> pList, List<TarefaBean> tList, Integer projetoId) {
		if (pList == null) pList = new ArrayList<ProjetoBean>();
		if (tList == null) tList = new ArrayList<TarefaBean>();
		if (projetoId == null) projetoId = 1;


		StringBuilder sb = new StringBuilder();


		sb.append("	<div id=\"canvas\">                                                                                                  " + PL);
		sb.append("		<div id=\"toolbar\" class=\"ui-widget-content ui-corner-all\">                                                   " + PL);
		sb.append("		  <div class=\"toolbar-column\">                                                                                          " + PL);
		sb.append("			<button id=\"novoProjetoButton\" class=\"button\">Projeto</button>                                           " + PL);
		sb.append("			<button id=\"novaTarefaButton\" class=\"button\">Tarefa</button>                                             " + PL);
		sb.append("		  </div>                                                                                          " + PL);
		
		
		sb.append("		  <div class=\"toolbar-column partB\">                                                                                          " + PL);
		sb.append("			<form class=\"ui-form\" method=\"post\" action=\"canvas\">                                                                     " + PL);
		sb.append("			  <select id=\"field-projetoId\" name=\"projetoId\" class=\"ui-widget-content ui-corner-all\">                                                                                                     " + PL);
		for (ProjetoBean pBean : pList) {
			String part = "";
			if (projetoId.equals(pBean.getId())) part = " selected=\"selected\"";
			
			sb.append("			    <option value=\""+ pBean.getId() +"\""+ part +">"+ pBean.getDescricao() +"</option>                            " + PL);
		}
		sb.append("			  </select>                                                                                                     " + PL);
		sb.append("			</form>                                                                                                      " + PL);
		sb.append("		  </div>                                                                                          " + PL);
		sb.append("		</div>                                                                                                           " + PL);
		sb.append("		<div class=\"ui-helper-reset\" />                                                                                " + PL);
		sb.append("		<div id=\"title-como\" class=\"title\">Como?</div>                                                               " + PL);
		sb.append("		<div id=\"title-oque\" class=\"title\">O que?</div>                                                              " + PL);
		sb.append("		<div id=\"title-paraquem\" class=\"title\">Para quem?</div>                                                      " + PL);
		sb.append("		<div class=\"ui-helper-reset\" />                                                                                " + PL);


		sb.append( createBlock(tList, projetoId, 7, "Parcerias principais") );

		sb.append("		<div id=\"box23\" class=\"float\">" + PL);
		sb.append( createBlock(tList, projetoId, 8, "Atividades principais") );
		sb.append( createBlock(tList, projetoId, 6, "Recursos principais") );
		sb.append("		</div>" + PL);
		
		sb.append( createBlock(tList, projetoId, 1, "Proposta de valor") );
		
		sb.append("		<div id=\"box56\" class=\"float\">" + PL);
		sb.append( createBlock(tList, projetoId, 4, "Relacionamento com clientes") );
		sb.append( createBlock(tList, projetoId, 3, "Canais") );
		sb.append("		</div>" + PL);
		
		sb.append( createBlock(tList, projetoId, 2, "Seguimento de clientes") );
		sb.append( createBlock(tList, projetoId, 9, "Estrutura de custos") );
		sb.append( createBlock(tList, projetoId, 5, "Receitas") );


		sb.append("		<div class=\"ui-helper-reset\">&nbsp;</div>                                                                      " + PL);
		sb.append("		<div id=\"title-quanto\" class=\"title\">Quanto?</div>                                                           " + PL);
		sb.append("		<div id=\"boxs\"></div>                                                                                          " + PL);
		sb.append("	</div>                                                                                                               " + PL);

		return sb.toString();
	}

	private static Object createBlock(List<TarefaBean> tList, Integer projetoId, int quadro, String title) {
		StringBuilder sb = new StringBuilder();
		sb.append("		<div id=\"box"+ quadro +"\"                                                                                                 " + PL);
		sb.append("			class=\"box float ui-widget ui-widget-content ui-corner-all\">                                               " + PL);
		sb.append("			<ul class=\"sortable\">                                                                                      " + PL);
		sb.append("			  <li class=\"box-title\">("+ quadro +") "+ title +" </li>                                                   " + PL);
		for (TarefaBean tBean : tList) {
			if (tBean.getQuadro().equals(quadro) == false) continue;
			if (tBean.getProjeto().getId().equals(projetoId) == false) continue;
			sb.append("<li class=\"item ui-corner-all\" style=\"background: #"+tBean.getCor()  +"\" tarefaId=\""+ tBean.getId() +"\">    " + PL);
			sb.append( tBean.getTitulo() );
			sb.append("</li>" + PL);
		}
		sb.append("			</ul>                                                                                                        " + PL);
		sb.append("		</div>                                                                                                           " + PL);

		return sb.toString();
	}




}
