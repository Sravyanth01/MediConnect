package com.example.MediConnect_Backend.controller;

import com.example.MediConnect_Backend.dto.responseDTO.notification.NotificationCountResponse;
import com.example.MediConnect_Backend.dto.responseDTO.notification.NotificationResponse;
import com.example.MediConnect_Backend.entity.User;
import com.example.MediConnect_Backend.repository.UserRepository;
import com.example.MediConnect_Backend.service.NotificationService;
import com.example.MediConnect_Backend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getMyNotifications(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {
        try {
            if (userDetails == null) {
                log.error("UserDetails is null - authentication failed");
                return ResponseEntity.status(401).body(ApiResponse.error("Authentication failed"));
            }

            String username = userDetails.getUsername();
            log.info("Getting notifications for user: {}", username);

            User currentUser = userRepository.findByEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + username));

            List<NotificationResponse> notifications = notificationService.getUnreadNotificationsForUser(currentUser);
            log.info("Found {} notifications for user: {}", notifications.size(), username);

            return ResponseEntity.ok(ApiResponse.success(notifications));
        } catch (IllegalArgumentException e) {
            log.error("Error getting notifications: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error getting notifications: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(ApiResponse.error("Failed to get notifications"));
        }
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {
        try {
            if (userDetails == null) {
                log.error("UserDetails is null - authentication failed");
                return ResponseEntity.status(401).body(ApiResponse.error("Authentication failed"));
            }

            String username = userDetails.getUsername();
            log.info("Marking notification as read: ID {}, User: {}", id, username);

            User currentUser = userRepository.findByEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + username));

            NotificationResponse notification = notificationService.markAsRead(id, currentUser);
            return ResponseEntity.ok(ApiResponse.success(notification));
        } catch (IllegalArgumentException e) {
            log.error("Error marking notification as read: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error marking notification as read: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(ApiResponse.error("Failed to mark notification as read"));
        }
    }

    @PatchMapping("/read-all")
    public ResponseEntity<?> markAllAsRead(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {
        try {
            if (userDetails == null) {
                log.error("UserDetails is null - authentication failed");
                return ResponseEntity.status(401).body(ApiResponse.error("Authentication failed"));
            }

            String username = userDetails.getUsername();
            log.info("Marking all notifications as read for user: {}", username);

            User currentUser = userRepository.findByEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + username));

            List<NotificationResponse> notifications = notificationService.markAllAsRead(currentUser);
            log.info("Marked {} notifications as read for user: {}", notifications.size(), username);

            return ResponseEntity.ok(ApiResponse.success(notifications));
        } catch (IllegalArgumentException e) {
            log.error("Error marking all notifications as read: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error marking all notifications as read: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(ApiResponse.error("Failed to mark all notifications as read"));
        }
    }

    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<NotificationCountResponse>> getUnreadNotificationCount(
            @AuthenticationPrincipal UserDetails userDetails) { // <-- FIX 1: Use UserDetails

        try {
            if (userDetails == null) {
                log.error("UserDetails is null - authentication failed");
                return ResponseEntity.status(401).body(ApiResponse.error("Authentication failed"));
            }

            String username = userDetails.getUsername();
            log.info("getUnreadNotificationCount called for user email: {}", username);

            User currentUser = userRepository.findByEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + username));

            log.info("Successfully fetched user ID: {}", currentUser.getId());

            NotificationCountResponse countResponse = notificationService.countUnreadNotifications(currentUser);
            log.info("Service returned unread count: {}", countResponse.getUnreadCount());

            return ResponseEntity.ok(ApiResponse.success(countResponse));

        } catch (IllegalArgumentException e) {
            log.error("Error in getUnreadNotificationCount: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

}
