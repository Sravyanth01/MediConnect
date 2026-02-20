import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class NotificationService {
    private apiUrl = `${environment.apiUrl}/notifications`;

    constructor(private http: HttpClient) { }

    getUnreadNotifications(): Observable<any> {
        return this.http.get<any>(this.apiUrl);
    }

    markNotificationAsRead(id: number): Observable<any> {
        return this.http.patch<any>(`${this.apiUrl}/${id}/read`, {});
    }

    markAllNotificationsAsRead(): Observable<any> {
        return this.http.patch<any>(`${this.apiUrl}/read-all`, {});
    }

    getUnreadCount(): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/unread-count`);
    }
}