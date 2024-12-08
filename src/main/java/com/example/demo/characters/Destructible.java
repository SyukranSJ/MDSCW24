package com.example.demo.characters;
/**
 * Interface representing an object that can take damage and be destroyed.
 * <p>
 * Any class implementing this interface should provide mechanisms to handle damage,
 * destroy the object, and check whether it has been destroyed. This interface is
 * typically implemented by game entities like characters, projectiles, or destructible objects.
 * </p>
 */
public interface Destructible {
    
     /**
      * Handles the object taking damage.
      * <p>
      * Implementations of this method should define the logic for reducing the object's health,
      * changing its state, or transitioning it into a destroyed state.
      * </p>
      */
     void takeDamage();
 
     /**
      * Destroys the object, marking it as unusable or removing it from the game environment.
      * <p>
      * This method should set the object's state to indicate it is no longer functional or visible
      * in the game, depending on the game's mechanics and visual representation.
      * </p>
      */
     void destroy();
 
     /**
      * Checks if the object has been destroyed.
      * 
      * @return {@code true} if the object is destroyed, {@code false} otherwise.
      * <p>
      * Implementing classes should return a boolean indicating whether the object has been removed
      * from the game or its internal state signifies destruction.
      * </p>
      */
     boolean isDestroyed();
 }
 
