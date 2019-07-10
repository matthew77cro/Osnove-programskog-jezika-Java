package hr.fer.zemris.tecaj.swing.p15;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class Prozor7b extends JFrame {

	private static final long serialVersionUID = 1L;

	public Prozor7b() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("Moj prvi prozor!");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}

	static class DemoListModel<T> implements ListModel<T> {
		private List<T> elementi = new ArrayList<>();
		private List<ListDataListener> promatraci = new ArrayList<>();
		
		@Override
		public void addListDataListener(ListDataListener l) {
			promatraci.add(l);
		}
		@Override
		public void removeListDataListener(ListDataListener l) {
			promatraci.remove(l);
		}
		
		@Override
		public int getSize() {
			return elementi.size();
		}
		@Override
		public T getElementAt(int index) {
			return elementi.get(index);
		}
		
		public void add(T element) {
			int pos = elementi.size();
			elementi.add(element);
			
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
			for(ListDataListener l : promatraci) {
				l.intervalAdded(event);
			}
		}
		public void remove(int pos) {
			elementi.remove(pos);
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, pos, pos);
			for(ListDataListener l : promatraci) {
				l.intervalRemoved(event);
			}
		}
	}
	
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		DemoListModel<Integer> model = new DemoListModel<>();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

		JButton dodaj = new JButton("Dodaj");
		bottomPanel.add(dodaj);
		
		JButton obrisi = new JButton("Obriši");
		bottomPanel.add(obrisi);
		
		Random rand = new Random();
		dodaj.addActionListener(e -> {
			model.add(rand.nextInt());
		});
		obrisi.addActionListener(e -> {
			int index = list1.getSelectedIndex();
			if(index != -1) {
				model.remove(index);
			}
		});

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));
		
		cp.add(central, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Prozor7b();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
