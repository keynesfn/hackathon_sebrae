package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.CanvasView;
import bean.ProjetoBean;
import bean.TarefaBean;
import bean.TarefaComentarioBean;
import core.db.TransactionDB;
import core.db.UserContext;
import core.exceptions.NotFoundException;
import core.html.ResultHtml;
import core.utils.ServletUtils;

public class CanvasServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doExecute(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doExecute(request, response);
	}
	public void doExecute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			String acao = request.getParameter("acao");
			boolean imprimirTelaInicial = true;
			if (acao == null) acao = "montarTela";



			UserContext userContext = ServletUtils.getUserContext(request);
			Integer projetoId = null;
			String projetoIdStr = request.getParameter("projetoId");
			if (projetoIdStr != null) {
				projetoId = Integer.parseInt(projetoIdStr);
				userContext.putObject("projetoId", projetoId);
			} else {
				projetoId = (Integer)userContext.getObject("projetoId");
			}

			StringBuilder sb = new StringBuilder();
			if (acao.equals("novoProjeto")) {

				String descricao = request.getParameter("descricao");
				String observacao = request.getParameter("observacao");
				TransactionDB trans = TransactionDB.getInstance();
				trans.start();
				ProjetoBean pBean = (ProjetoBean) trans.create(ProjetoBean.class);
				pBean.setCadastro(userContext.getDataAtual());
				pBean.setDescricao(descricao);
				pBean.setObservacao(observacao);
				pBean.getUsuario().setId(userContext.getUsuario().getId());

				trans.insert(pBean);
				trans.commit();
				trans.close();				

			} else if (acao.equals("salvarTarefa")) {

				// inserir no banco de dados vei!!!
				String titulo = request.getParameter("titulo");
				String descricao = request.getParameter("descricao");
				String cor = request.getParameter("cor");
				String quadroStr = request.getParameter("quadro");
				Integer quadro = (quadroStr == null ? 7 : Integer.parseInt(quadroStr));
				String situacaoStr = request.getParameter("situacao");
				Integer situacao = (situacaoStr == null ? 1 : Integer.parseInt(situacaoStr));

				// inserir no banco de dados agora
				TransactionDB trans = TransactionDB.getInstance();
				trans.start();
				TarefaBean tBean = (TarefaBean) trans.create(TarefaBean.class);
				tBean.getProjeto().setId(projetoId);
				tBean.setCadastro(userContext.getDataAtual());
				tBean.setTitulo(titulo);
				tBean.setDescricao(descricao);
				tBean.setCor(cor);
				tBean.setSituacao(situacao);
				tBean.setQuadro(quadro);

				trans.insert(tBean);
				trans.commit();
				trans.close();

				imprimirTelaInicial = false;
				sb.append(tBean.getId());
			} else if (acao.equals("alterarQuadro")) {
				Integer tarefaId = Integer.parseInt(request.getParameter("tarefaId"));
				String quadroStr = request.getParameter("quadro");
				Integer quadro = (quadroStr == null ? 1 : Integer.parseInt(quadroStr));

				TransactionDB trans = TransactionDB.getInstance();
				trans.start();
				TarefaBean tBean = (TarefaBean) trans.selectById(TarefaBean.class, tarefaId);
				tBean.setQuadro(quadro);
				trans.update(tBean);
				trans.commit();
				trans.close();
			} else if (acao.equals("apagarTarefa")) {
				Integer tarefaId = Integer.parseInt(request.getParameter("tarefaId"));

				TransactionDB trans = TransactionDB.getInstance();
				trans.start();

				// primeiro excluir os comentarios 
				List<TarefaComentarioBean> tcList = (List<TarefaComentarioBean>) trans.selectAll(TarefaComentarioBean.class);
				for (TarefaComentarioBean tcBean : tcList) {
					if (tcBean.getTarefa().getId().equals(tarefaId) == false) continue;

					trans.delete(tcBean);
				}


				// depois excluir a tarefa
				TarefaBean tBean = (TarefaBean) trans.selectById(TarefaBean.class, tarefaId);
				trans.delete(tBean);
				trans.commit();
				trans.close();

			} else if (acao.equals("comentarTarefa")) {

				String comentario = request.getParameter("comentario");
				Integer tarefaId = Integer.parseInt(request.getParameter("tarefaId"));

				TransactionDB trans = TransactionDB.getInstance();
				trans.start();
				TarefaComentarioBean tcBean = (TarefaComentarioBean) trans.create(TarefaComentarioBean.class);
				tcBean.getTarefa().setId(tarefaId);
				tcBean.setData(userContext.getDataAtual());
				tcBean.setHora(userContext.getHoraAtual());
				tcBean.setComentario(comentario);

				trans.insert(tcBean);
				trans.commit();
				trans.close();
			} else if (acao.equals("buscarComentario")) {

				Integer tarefaId = Integer.parseInt(request.getParameter("tarefaId"));
				TransactionDB trans = TransactionDB.getInstance();
				List<TarefaComentarioBean> tcTemp = (List<TarefaComentarioBean>) trans.selectAll(TarefaComentarioBean.class);


				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				StringBuilder retorno = new StringBuilder();
				for (TarefaComentarioBean tcBean : tcTemp) {
					if (tcBean.getTarefa().getId().equals(tarefaId) == false) continue;

					retorno.append("<b>");
					retorno.append( sdf.format( tcBean.getData() ) );
					retorno.append(" - ");
					retorno.append( tcBean.getHora() );
					retorno.append("</b>");
					retorno.append(": ");
					retorno.append( tcBean.getComentario() );
					retorno.append("<br />");
				}
				sb.append(retorno.toString());
				imprimirTelaInicial = false;
			}


			if (imprimirTelaInicial == true) {
				// pegando algum caso o usuario ja tenha a parada toda
				// eu sei que nao é para fazer assim...
				// como o tempo é pequeno to fazendo assim mesmo
				// mas o certo é vir do banco ja os registros que vou trabalhar
				TransactionDB trans = TransactionDB.getInstance();

				List<ProjetoBean> pList = null;
				List<TarefaBean> tList = null;
				try {
					pList = (List<ProjetoBean>)trans.selectAll(ProjetoBean.class);
					if (projetoId == null) {
						projetoId = pList.get(0).getId();
						userContext.putObject("projetoId", projetoId);
					}
				} catch (NotFoundException e) {
					pList = new ArrayList<ProjetoBean>();
				}
				try {
					tList = (List<TarefaBean>)trans.selectAll(TarefaBean.class);
				} catch (NotFoundException e) {
					tList = new ArrayList<TarefaBean>();
				}

				ResultHtml html = new ResultHtml();
				html.addBody( CanvasView.criaHtmlNovaProjetoWindow() );
				html.addBody( CanvasView.criaHtmlNovaTarefaWindow() );
				html.addBody( CanvasView.criaHtmlTarefaWindow() );
				html.addBody( CanvasView.criaHtmlHome(pList, tList, projetoId) );

				sb.append( html );
			}


			out.print(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
