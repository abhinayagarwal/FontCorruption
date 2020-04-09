import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FontCorruption extends Application {
    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();

        webView.getEngine().getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                final PrinterJob printerJob = PrinterJob.createPrinterJob();
                printerJob.jobStatusProperty().addListener((o, ov, nv) -> {
                    if (nv == PrinterJob.JobStatus.DONE || nv == PrinterJob.JobStatus.CANCELED) {
                        Platform.exit();
                    }
                });
                printerJob.showPrintDialog(stage);
                final boolean success = printerJob.printPage(webView);
                if (success) {
                    printerJob.endJob();
                }
            }
        });

        stage.setScene(new Scene(webView));
        webView.getEngine().load( getClass().getResource("/CentraNo2.html").toString());
        stage.show();
    }
}
