package core.html;

public class ResultHtml {

	public static final String VERSAO = "1";
	public static final String PL = "\n";
	
	public StringBuilder body = new StringBuilder();
	public StringBuilder script = new StringBuilder();
	
	
	public void addBody(String part) {
		body.append(part);
	}
	public void addScript(String part) {
		
		script.append(part);
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>                                                                                                                " + PL);
		sb.append("<head>                                                                                                                " + PL);
		sb.append("<title>Desafio SEBRAE!!!</title>                                                                                      " + PL);
		sb.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"./static/css/redmond/ui-all.css?v="+ VERSAO +"\" />                 " + PL);
		sb.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"./static/css/fw-global.css?v="+ VERSAO +"\" />                      " + PL);
		sb.append("<script type=\"text/javascript\" src=\"./static/js/jquery.js?v="+ VERSAO +"\"></script>                               " + PL);
		sb.append("<script type=\"text/javascript\" src=\"./static/js/jquery-ui.js?v="+ VERSAO +"\"></script>                            " + PL);
		sb.append("<script type=\"text/javascript\" src=\"./static/js/jquery-impl.js?v="+ VERSAO +"\"></script>                           " + PL);
		
		sb.append("<script>                                                                                                              " + PL);
		sb.append("$(document).ready(function() {                                                                                        " + PL);
		sb.append( script.toString() );
		sb.append("});                                                                                                                   " + PL);
		sb.append("</script>                                                                                                             " + PL);
		
		
		sb.append("</head>                                                                                                               " + PL);
		sb.append("<body>                                                                                                                " + PL);

		sb.append( body.toString() );
		
		sb.append("</body>                                                                                                               " + PL);
		sb.append("</html>                                                                                                               " + PL);
		
		return sb.toString();
	}
	
}
