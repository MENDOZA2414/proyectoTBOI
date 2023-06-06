import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PanelLeaderBoard extends JPanel {
	JButton btnMenu;
	public PanelLeaderBoard(String ruta, ArrayList<Object> lista) {
		setLayout(null);

		//Fondo de inicio
		JPanel fondo = new JPanel();
		fondo.setBackground(Color.white);
		fondo.setSize(1080, 690);
		fondo.setLocation(0,0);
		
		ImageIcon icono = new ImageIcon(ruta+"botonMenu.png");
        btnMenu = new JButton(icono);
        
        btnMenu.setSize(icono.getIconWidth(), icono.getIconHeight());
        btnMenu.setLocation(430, 480);
        btnMenu.setOpaque(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setBorderPainted(false);
       // btnSiguiente.setFocusPainted(false);		
		Imagen fondoInicio = new Imagen(ruta+"leaderBoard.png",1080,650,fondo);
		
		
		//table = new JTable();
				DefaultTableModel tableModel = new DefaultTableModel();
		        tableModel.addColumn("Name");
		        tableModel.addColumn("Score");
		    
		     // Iterar sobre la lista y agregar los datos al tableModel
		        for (Object obj : lista) {
		            if (obj instanceof Score) {
		                Score score = (Score) obj;
		                Object[] rowData = {score.getNombre(), score.getScore()};
		                tableModel.addRow(rowData);
		            }
		        }


		        JTable leaderboardTable = new JTable(tableModel);
		        leaderboardTable.setShowGrid(false);
		        leaderboardTable.setShowVerticalLines(false);
		        leaderboardTable.setShowHorizontalLines(false);
		        
		     // Renderizador personalizado para centrar los datos en todas las celdas
		        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		        leaderboardTable.setDefaultRenderer(Object.class, centerRenderer);
		        
		        // Cambiar el tipo de fuente para la tabla
		        Font font = new Font("Matura MT Script Capitals", Font.PLAIN, 15);
		        leaderboardTable.setFont(font);
		        
		        // Cambiar el tipo de fuente para las celdas de encabezado
		        Font headerFont = new Font("Matura MT Script Capitals", Font.BOLD, 17);
		        JTableHeader tableHeader = leaderboardTable.getTableHeader();
		        tableHeader.setFont(headerFont);
		        tableHeader.setBackground(Color.decode("#eae2e0"));
		        
		        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
		        scrollPane.setSize(500,200);
		        scrollPane.setLocation(275, 230);
		
		add(btnMenu);
		add(scrollPane);
		add(fondo);
		
		repaint();
		revalidate();
	}
	public JButton getBtnMenu() {
		return btnMenu;
	}
}
