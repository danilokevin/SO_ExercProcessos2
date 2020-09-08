package view;

import javax.swing.JOptionPane;

import controller.ProcessController;

public class Main {
	public static void main(String[] args) {
		ProcessController pc = new ProcessController();
		
		String process = "";
		int menu = 0, system = 0;
		
		while (menu != 9){
			menu = Integer.parseInt(JOptionPane.showInputDialog("Escolha a opção: \n1 - Obter Sistema Operacional \n2 - Listas processos ativos \n3 - Encerrar processos via nome ou pid \n9 - Sair"));
			
			switch (menu){
			case 1:
				system = pc.os();
				break;
			case 2:
				if (system == 0){
					JOptionPane.showMessageDialog(null, "Obter sistema operacional na opção 1");
				} else {
					if (system == 1) {
						process = "tasklist /fo table";
					} else {
						process = "ps -ef";
					}

					pc.readProcess(process);
				}	
				break;
			case 3:
				String param = JOptionPane.showInputDialog("Informe o nome ou pid do processo a ser encerrado:");
				pc.killProcess(param, system);
				break;
			case 9:
				JOptionPane.showMessageDialog(null, "Programa finalizado");
				break;
			default:
				JOptionPane.showMessageDialog(null, "Opção inválida");
			}
		}
	}
}

