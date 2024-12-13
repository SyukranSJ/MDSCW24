package com.example.demo.manager;

import java.util.List;

import com.example.demo.characters.ActiveActorDestructible;
import com.example.demo.characters.UserPlane;

/**
 * The CollisionManager class handles collision detection and resolution for the game.
 * It manages collisions between friendly units, enemy units, and projectiles.
 */
public class CollisionManager {
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final UserPlane user;
    private final double screenWidth;

    /**
     * Constructs a new CollisionManager instance with the specified lists of actors and the user plane.
     *
     * @param friendlyUnits the list of friendly units
     * @param enemyUnits the list of enemy units
     * @param userProjectiles the list of user projectiles
     * @param enemyProjectiles the list of enemy projectiles
     * @param user the user plane
     * @param screenWidth the width of the screen
     */
    public CollisionManager(
            List<ActiveActorDestructible> friendlyUnits,
            List<ActiveActorDestructible> enemyUnits,
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyProjectiles,
            UserPlane user,
            double screenWidth) {
        this.friendlyUnits = friendlyUnits;
        this.enemyUnits = enemyUnits;
        this.userProjectiles = userProjectiles;
        this.enemyProjectiles = enemyProjectiles;
        this.user = user;
        this.screenWidth = screenWidth;
    }

    /**
     * Handles all types of collisions in the game.
     * This includes plane collisions, user projectile collisions, enemy projectile collisions, and enemy penetration.
     */
    public void handleCollisions() {
        handlePlaneCollisions();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handleEnemyPenetration();
    }

    /**
     * Handles collisions between friendly units and enemy units.
     */
    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     */
    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     */
    private void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Handles collisions between two lists of actors.
     *
     * @param actors1 the first list of actors
     * @param actors2 the second list of actors
     */
    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor1 : actors1) {
            for (ActiveActorDestructible actor2 : actors2) {
                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
                    actor1.takeDamage();
                    actor2.takeDamage();
                }
            }
        }
    }

    /**
     * Handles enemy penetration by checking if any enemy has moved past the screen width.
     * If an enemy penetrates the defenses, the user takes damage and the enemy is destroyed.
     */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    /**
     * Checks if an enemy has penetrated the defenses by moving past the screen width.
     *
     * @param enemy the enemy to check
     * @return true if the enemy has penetrated the defenses, false otherwise
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }
}
