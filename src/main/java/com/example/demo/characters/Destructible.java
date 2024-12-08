package com.example.demo.characters;
/**
 * Interface representing an object that can be damaged and destroyed.
 */
public interface Destructible {

	 /**
     * Method to handle the object taking damage.
     * @return boolean indicating whether the object survived or was destroyed.
     */
	void takeDamage();
 	 /**
     * Destroys the object, marking it as unusable.
     */
	void destroy();
	/**
     * Checks if the object has been destroyed.
     * @return true if the object is destroyed, false otherwise.
     */
    boolean isDestroyed();
	
}
