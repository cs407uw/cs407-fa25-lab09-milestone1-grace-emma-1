package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }
        val dt = dT.coerceAtMost(0.05f) // 50 ms cap
        if (dt <= 0f) return

        val a0x = accX
        val a0y = accY
        val a1x = xAcc
        val a1y = yAcc
        // new velocity = old velocity + 1/2(a1 + a0) * (t1 - t0)
        val newVelocityX = velocityX + 0.5f * (a1x + a0x) * dt
        val newVelocityY = velocityY + 0.5f * (a1y + a0y) * dt

        val newPosX = velocityX * dt + (dt * dt / 6f) * (3f * a0x + a1x)
        val newPosY = velocityY * dt + (dt * dt / 6f) * (3f * a0y + a1y)

        accX = a1x
        accY = a1y
        posX += newPosX
        posY += newPosY
        velocityX = newVelocityX
        velocityY = newVelocityY
        checkBoundaries()
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // (Check all 4 walls: left, right, top, bottom)
        if (posX < 0f) { // left wall
            posX = 0f
            velocityX = 0f

        } else if (posX > backgroundWidth - ballSize) { // right wall
            posX = backgroundWidth - ballSize
            velocityX = 0f
        }
        if (posY < 0f) { // top wall
            posY = 0f
            velocityY = 0f

        } else if (posY > backgroundHeight - ballSize) { // bottom wall
            posY = backgroundHeight - ballSize
            velocityY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // (Reset posX, posY, velocityX, velocityY, accX, accY, isFirstUpdate)
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }
}