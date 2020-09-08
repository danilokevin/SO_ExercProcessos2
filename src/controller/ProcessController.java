package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class ProcessController {
	
	public ProcessController(){
		super();
	}
	
	public int os(){
		int option = 0;
		String os = System.getProperty("os.name");
		JOptionPane.showMessageDialog(null, "Sistema Operacional em execução: " + os);
		
		if (os.contains("Windows")){
			option = 1;
		} else {
			option = 2;
		}
		return option;
	}
	
	public void readProcess(String process){
		
		try {
			Process p = Runtime.getRuntime().exec(process);
			InputStream fluxo = p.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			while (linha != null) { //
				System.out.println(linha);
				
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void killProcess(String param, int os){
		String cmdPid = "";
		String cmdNome = "";
		
		if (os == 1){
			cmdPid = "TASKKILL /PID";
			cmdNome = "TASKKILL /IM";
		} else {
			cmdPid = "KILL /PID";
			cmdNome = "KILL /IM";
		}
		
		int pid = 0;
		StringBuffer buffer = new StringBuffer();
		
		//NumberFormatException
		try {
			 //"TASKKILL /PID XXXX"
			pid = Integer.parseInt(param);
			buffer.append(cmdPid);
			buffer.append(" ");
			buffer.append(pid);
		} catch (NumberFormatException e) {
			//TASKKILL /IM nomedoprocesso.exe
			buffer.append(cmdNome);
			buffer.append(" ");
			buffer.append(param);	
		}
		
		
		callProcess(buffer.toString());
		
	}
	
	public void callProcess(String process) {
		try {
			Runtime.getRuntime().exec(process); //tenta processar, se não der
		} catch (Exception e) { //joga o erro para a variável 'e'
			String msgErro = e.getMessage(); //capturo a mensagem de erro
		
			if (msgErro.contains("740")){ //verifico se nesta mensagem contem 740 que significa que
				//programa que requer acesso de administrador (elevação)
				//o caminho para abrir programa que pede autorização é:
				//cmd /c caminho_do_processo
				StringBuffer buffer = new StringBuffer();
				//criei um buffer para montar o caminho acima
				buffer.append("cmd /c");
				buffer.append(" ");
				buffer.append(process);
				try { //tento processar novamente
					Runtime.getRuntime().exec(buffer.toString()); //passo o buffer como String
				} catch (IOException e1) { 
					e1.printStackTrace(); //caso não seja erro 740 ainda ele vai me mostrar o erro
				}
			} else {
				System.err.println(msgErro); //caso a mensagem não contenha 740 vai me mostrar o erro, somente linha 1
			}
		}
	}

}
