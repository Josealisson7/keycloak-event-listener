package br.com.alisson;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;

import java.util.List;
import java.util.Map;

public class CustomEventListenerProvider implements EventListenerProvider {

    private final KeycloakSession session;
    private final RealmProvider model;

    public CustomEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {
        if (EventType.LOGIN.equals(event.getType())) {
            System.out.println("EVENT - LOGIN");
            Map<String, List<String>> userData = this.getUserData(event);
            System.out.println(userData);
        }

        if (EventType.REGISTER.equals(event.getType())) {
            System.out.println("EVENT - REGISTER");
            Map<String, List<String>> userData = this.getUserData(event);
            System.out.println(userData);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
    }

    public Map<String, List<String>> getUserData(Event event) {
        RealmModel realm = this.model.getRealm(event.getRealmId());
        UserModel newRegisteredUser = this.session.users().getUserById(realm, event.getUserId());
        Map<String, List<String>> userData = newRegisteredUser.getAttributes();
        return userData;
    }

    @Override
    public void close() {

    }
}
