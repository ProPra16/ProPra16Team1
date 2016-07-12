package logic;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
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
		timeAxis.setLabel("Zeit in Sekunde");
		XYChart.Series red = new XYChart.Series<>();
		XYChart.Series green = new XYChart.Series<>();
		XYChart.Series refactor = new XYChart.Series<>();
		red.setName("RED Phase");
		green.setName("GREEN Phase");
		refactor.setName("Refactor Phase");
		int countRed = 0;
		int countGreen = 0;
		int countRef = 0;
		String analysis = "";
		//making the analyses
		for(TrackingInfo trInfo : store.getStoredItems()){
			if(trInfo.getStage().equals("red")){
				countRed++;
				if(trInfo.hasErrors()){
					analysis += "\nBei Ihrem " + countRed + "ten Mal in RED Phase haben Sie folgenden Fehler :" + trInfo.getErrorMessage();
				}else{
					analysis += "\nBei Ihrem " + countRed + "ten Mal in RED Phase haben Sie keine Fehler!";
				}	
				red.getData().add(new XYChart.Data(String.valueOf(countRed) + " mal",trInfo.getTime()));
			}
			if(trInfo.getStage().equals("green")){
				countGreen++;
				if(trInfo.hasErrors()){
					analysis += "\nBei Ihrem " + countGreen + "ten Mal in GREEN Phase haben Sie folgenden Fehler :" + trInfo.getErrorMessage();
				}else{
					analysis += "\nBei Ihrem " + countGreen + "ten Mal in Green Phase haben Sie keine Fehler!";
				}	
				green.getData().add(new XYChart.Data(String.valueOf(countGreen) + " mal",trInfo.getTime()));
			}
			if(trInfo.getStage().equals("refactor")){
				countRef++;
				if(trInfo.hasErrors()){
					analysis += "\nBei Ihrem " + countRef + "ten Mal in Refactor Phase haben Sie folgenden Fehler :" + trInfo.getErrorMessage();
				}else{
					analysis += "\nBei Ihrem " + countRef + "ten Mal in Refactor Phase haben Sie keine Fehler!";
				}	
				refactor.getData().add(new XYChart.Data(String.valueOf(countRef) + " mal",trInfo.getTime()));
			}
		}
		chart.getData().addAll(red,green,refactor);
		chart.setPrefSize(800, 600);
		
		VBox root = new VBox(20);
		
		TextArea description = new TextArea();
		description.setEditable(false);
		
		
		description.setText(analysis);
		root.getChildren().addAll(chart,description);
		Scene scene = new Scene(root,800,800);
		
		
		window.setScene(scene);
		window.showAndWait();
	}
}
