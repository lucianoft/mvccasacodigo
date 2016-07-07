import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Chave {

	public Date createDate(int dia, int mes, int ano) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(ano, mes - 1, dia, 0, 0, 0);
		//f  
		return calendario.getTime();
	}
	
	public String formatDate(Date date, String format) {
		if (date != null && format != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		}
		return "";
	}
	private String lpadTo(String input, int width, char ch) {
		String strPad = "";
	
		StringBuffer sb = new StringBuffer(input.trim());
		while (sb.length() < width){
			sb.insert(0, ch);
		}	
		strPad = sb.toString();
	
		if (strPad.length() > width) {
			strPad = strPad.substring(0, width);
		}
		return strPad;
	}
	private String gerarChaveAcesso(String cUF,
			Date dtEmissao,
			String cnpjCpf,
			String mod,
			String serie,
			String tpEmis,
			String nNF,
			String cNF) {

		StringBuilder chave = new StringBuilder();

		chave.append(lpadTo(cUF, 2, '0'));
		chave.append(lpadTo(formatDate(dtEmissao, "MMyy"), 4, '0'));
		chave.append(lpadTo(cnpjCpf.replaceAll("\\D", ""), 14, '0'));
		chave.append(lpadTo(mod, 2, '0'));
		chave.append(lpadTo(serie, 3, '0'));
		chave.append(lpadTo(String.valueOf(nNF), 9, '0'));
		chave.append(lpadTo(tpEmis, 1, '0'));
		chave.append(lpadTo(cNF, 8, '0'));
		chave.append(modulo11(chave.toString()));

		//chave.insert(0, "NFe");
		
		return chave.toString();
	}
	
	private int modulo11(String chave) {
		int total = 0;
		int peso = 2;
	
		for (int i = 0; i < chave.length(); i++) {
			total += (chave.charAt((chave.length() - 1) - i) - '0') * peso;
			peso++;
			if (peso == 10)
				peso = 2;
		}
		int resto = total % 11;
		return (resto == 0 || resto == 1) ? 0 : (11 - resto);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chave chave = new Chave();
		String cUF = "31";
		Date dtEmissao = chave.createDate(11, 12, 2015);
		String cnpjCpf = "71371249000828";
		String mod = "55";
		String serie = "1";
		String tpEmis = "1";
		String nNF = "54285";
		String cNF = "54285";
		System.out.println(chave.gerarChaveAcesso(cUF, dtEmissao, cnpjCpf, mod, serie, tpEmis, nNF, cNF));
	}

}
