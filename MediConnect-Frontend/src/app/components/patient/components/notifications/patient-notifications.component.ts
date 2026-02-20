import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from '../../../../services/notification.service';
import { AuthService } from '../../../../services/auth.service';
import { Notification } from '../../../../models/types';

@Component({
    selector: 'app-patient-notifications',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './patient-notifications.component.html',
    styleUrls: ['./patient-notifications.component.css']
})
export class PatientNotificationsComponent implements OnInit, OnDestroy {
    notifications: Notification[] = [];
    loading = true;
    private intervalId: any;

    constructor(
        private notificationService: NotificationService,
        private authService: AuthService
    ) { }

    ngOnInit() {
        this.fetchNotifications();
        this.intervalId = setInterval(() => this.fetchNotifications(), 20000);
    }

    ngOnDestroy() {
        if (this.intervalId) {
            clearInterval(this.intervalId);
        }
    }

    fetchNotifications() {
        const user = this.authService.getCurrentUser();
        if (!user) return;

        
        this.loading = this.notifications.length === 0; 
        this.notificationService.getUnreadNotifications().subscribe({
            next: (data) => {
                // Filter by current user ID
                this.notifications = data.filter(n => n.userId === user.id);
                this.loading = false;
            },
            error: (err) => {
                console.error(err);
                this.loading = false;
            }
        });
    }

    handleMarkAsRead(id: number) {
        this.notificationService.markNotificationAsRead(id).subscribe({
            next: () => {
                this.notifications = this.notifications.filter(n => n.id !== id);
            },
            error: (err) => console.error(err)
        });
    }

    handleMarkAllAsRead() {
        if (this.notifications.length === 0) return;
        this.notificationService.markAllNotificationsAsRead().subscribe({
            next: () => {
                this.notifications = [];
            },
            error: (err) => console.error(err)
        });
    }
}
