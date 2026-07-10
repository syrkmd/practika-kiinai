package com.yvl.vorstu.entities;

public enum OutboxEventStatus {
    // Not yet attempted, or a previous attempt failed transiently and is
    // waiting to be retried (see attempts / nextAttemptAt on OutboxEvent).
    // This is the only status the relay worker needs to poll.
    PENDING,
    // Currently claimed by a worker for delivery.
    PROCESSING,
    // Terminal: delivered successfully.
    SENT,
    // Terminal: attempts exhausted, worker gave up, needs manual attention.
    // A single failed attempt that is still eligible for retry goes back
    // to PENDING instead of here.
    FAILED
}
