package logic;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class ChartWindow {

	public void show(TrackingStore store){
		Stage window = new Stage();
		window.setTitle("Tracking Chart");
		CategoryAxis stageAxis = new CategoryAxis();
		NumberAxis timeAxis = new NumberAxis();
		BarChart<String, Number> chart = new BarChart<>(stageAxis,timeAxis);
		chart.setTitle("Tracking fuer verschiedene Phasen");
		stageAxis.setLabel("Phase");
		timeAxis.setLabel("Zeit");
		XYChart.Series red = new XYChart.Series<>();
		XYChart.Series green = new XYChart.Series<>();
		XYChart.Series refactor = new XYChart.Series<>();
		red.setName("RED Phase");
		green.setName("GREEN Phase");
		refactor.setName("Refactor Phase");
		int countRed = 0;
		int countGreen = 0;
		int countRef = 0;
		for(TrackingInfo trInfo : store.getStoredItems()){
			if(trInfo.getStage().equals("red")){
				countRed++;
				red.getData().add(new XYChart.Data(String.valueOf(countRed) + " mal",trInfo.getTime()));
			}
			if(trInfo.getStage().equals("green")){
				countGreen++;
				green.getData().add(new XYChart.Data(String.valueOf(countGreen) + " mal",trInfo.getTime()));
			}
			if(trInfo.getStage().equals("refactor")){
				countRef++;
				refactor.getData().add(new XYChart.Data(String.valueOf(countRef) + " mal",trInfo.getTime()));
			}
		}
		Scene scene = new Scene(chart,800,800);
		chart.getData().addAll(red,green,refactor);
		window.setScene(scene);
		window.show();
	}
}
