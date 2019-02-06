import io.vertx.ext.web.handler.sse.EventSource;
import io.vertx.ext.web.handler.sse.exceptions.ConnectionRefusedException;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.Vertx;


public class EventSourceExamples {
  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    HttpClientOptions options = new HttpClientOptions();
    // https://stream.wikimedia.org/v2/stream/test
    options.setDefaultHost("stream.wikimedia.org");
    options.setDefaultPort(443);
    options.setSsl(true);
		options.setVerifyHost(true);		
  
    EventSource eventSource = EventSource.create(vertx, options);

    eventSource.connect("/v2/stream/test", handler -> {
			if (handler.succeeded()) {
				eventSource.onMessage(msg -> System.out.println("msg: " + msg));
			} else {
				ConnectionRefusedException cre = (ConnectionRefusedException)handler.cause();
				System.out.println("Server dropped me because : " + cre.statusCode() + " and he told me : "+cre.statusMessage());
			}
    });
  }
}

