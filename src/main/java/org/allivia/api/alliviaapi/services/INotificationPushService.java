package org.allivia.api.alliviaapi.services;

public interface INotificationPushService {
    Object sendPush(long idCita,  String titulo, String mensaje);
    Object sendPushDocOrPac(long id, String docORpac, String titulo, String mensaje);
}
