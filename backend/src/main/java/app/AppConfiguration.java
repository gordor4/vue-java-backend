package app;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class AppConfiguration {
    private Env environment;

    @PostConstruct
    private void setEnvironment() {
        environment = Env.SITE;
    }

    private enum Env {
        LOCAL("http://localhost:8080/rest-1.0-SNAPSHOT/api"),
        SITE("http://site.gordor.host"),
        DEV("http://gordor.host/rest/api");

        private final String text;

        Env(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public String getHostEnvironment() {
        return environment.text;
    }
}
