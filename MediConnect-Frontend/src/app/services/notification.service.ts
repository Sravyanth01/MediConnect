import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Notification } from '../models/types';
import { AuthService } from './auth.service';
import { environment } from '../../environments/environment';


@Injectable({
    providedIn: 'root'
})
export class NotificationService {
    private apiUrl = environment.apiUrl;

    constructor(
        private http: HttpClient,
        private authService: AuthService
    ) { }

    getUnreadNotifications(): Observable<Notification[]> {
        const user = this.authService.getCurrentUser();
        if (!user || !user.id) {
            return new Observable(observer => {
                observer.next([]);
                observer.complete();
            });
        }
        return this.http.get<Notification[]>(`${this.apiUrl}/notifications?read=false`).pipe(
            map(notifications => notifications.filter(n => n.userId === user.id))
        );
    }

    markNotificationAsRead(id: number): Observable<any> {
        return this.http.patch(`${this.apiUrl}/notifications/${id}`, { read: true });
    }

    markAllNotificationsAsRead(): Observable<any> {
        return new Observable(observer => {
            observer.next({ success: true });
            observer.complete();
        });
    }
}
