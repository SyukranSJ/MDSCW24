  **GITHUB LINK**
  
  https://github.com/SyukranSJ/MDSCW24

  ### CHANGES ###
  
><><><><><><><><><><><><>><<><><><><><><><><><>

## ALL CLASSES ###
* Add comments for Javadocs

# ShieldImage Class
* Correctly updated from "shield.jpg" to "shield.png" to reference the shield image, using the IMAGE_NAME variable with the getResource method.
* A loadShieldImage() method is introduced to ensure that if the image is not found, an exception is thrown, helping with debugging in case of errors.
* Renamed dimShield() to applyWeakenEffect() to make it clear that this applies the visual effect of weakening (dimming the shield). 
*Added Javadoc comments for better documentation of each method, explaining what they do and any parameters they use.
* The weakenShield() method was added to call the dimShield() method, which dims the shield's opacity to indicate it is weakened. The shield's visibility and opacity are managed within this class, keeping the visual logic separate from gameplay logic.

# Controller
* use a StringProperty (levelNameProperty) to manage the level's name dynamically and added a listener to trigger goToLevel when the level name changes.
* Added Map (LEVEL_CLASSES) is used to register all level classes, reducing reliance on string-based reflection. - Make it easy to add new levels without modifying core logic.
* Simplifying Error Handling with a showErrorDialog utility method centralizes error handling and prevents redundancy.
* Make it Dynamic Level Changes which will make switching levels is simpler and ensures type safety through the registry.
* added logic to handle goToLevel is separated from property change listeners.


# Main
* Extracted setupStage and initializeController into separate methods for clarity and better organization.
* Each method now serves a single purpose, making the code more readable and modular.
* Enhanced Error Handling - Added an alert dialog (handleError) to notify the user when an error occurs, improving user experience.
Included full stack trace logging for debugging purposes.
* Simplified Exception Handling by Combining the catch block into a single Exception type for simplicity since all exceptions are handled the same way.
* Extracted root cause (InvocationTargetException) handling into the handleError method.
* main entry point is now directly launching the MainMenu class.

# ActiveActor
* Extracting image loading to a separate method makes the constructor easier to understand at a glance.
* Centralized error handling by isolating resource loading (loadImage method), any changes to how resources are handled will affect only one method.
* Graceful handling of missing resources by logging or throwing meaningful exceptions ensures the program doesn’t fail silently.
* added fallback options- If an image fails to load, the program can show a placeholder or continue running with a warning, preventing crashes.

# ActiveActor Destructible
* Initialized isDestroyed directly at declaration (private boolean isDestroyed = false;), reducing the need for explicit initialization in the constructor.
* Retained proper encapsulation of the isDestroyed property using getter and setter methods.
* Kept the updatePosition, updateActor, and takeDamage methods abstract, enforcing subclasses to define specific behaviors.
* Centralized the logic for destruction (destroy and setDestroyed) ensures consistent handling of the actor's state.

# Boss
* correctly declaring and structured the shield image to make it visible and fully functional.
* added Boss healthbar
* added color that react based on remaining health
* added shield movement 
* move shield declaration from levelviewtwo to boss level.
* added a new features which is dimShield().
* The takeDamage() method was updated to use the weakenShield() method of the ShieldImage class instead of directly calling dimShield(). This change encapsulates the logic for dimming the shield inside the ShieldImage class, making the Boss class more readable and easier to maintain.

**Deleted:
private boolean isShielded;
All direct isShielded references.
**Replaced with :
boolean conditions with ShieldState enum comparisons.
Shield activation/deactivation methods to use ShieldState.
**Added:
ShieldState enum.
shieldState property.

# Boss Projectile
- change image and size.

# Destructible
* JavaDoc comments for better documentation.

# Enemy Plane
* Added a visibility check (isWithinScreenBounds()) to ensure the plane only fires projectiles when it is on-screen.
* Added a default constructor for simpler instantiation.
* Removed unused constants or variables to enhance clarity and reduce potential confusion.
* adjust projectile x and y position.

# Enemy Projectile
* updatePosition() method was added or adjusted to ensure the projectile moves left (negative X direction) with a constant velocity (HORIZONTAL_VELOCITY).
* Ensured proper overriding of updatePosition() and updateActor() to align with the parent class (Projectile), ensuring that the projectile behaves as expected in the game.
* Removed unused or redundant variables, such as constants that were defined but not referenced.



# FighterPlane
* change HealthAtZero from private to protected to allow future extensibility or subclass-specific logic.

# GameOver Image
* put in new image for GameOver
* adjusting the size of the image.

# Heart Display
*Storing the Image: The heart image is loaded once in the loadHeartImage() method, so it's reused in the initializeHearts() method, avoiding multiple calls to getClass().getResource().
* Default Heart Count Validation constructor to ensures that the number of hearts is always a non-negative value by validating heartsToDisplay > 0.
* Replaced INDEX_OF_FIRST_ITEM with a simple constant REMOVE_INDEX since it’s only used once.


# Level One
* checkIfGameOver method now calls handleGameOver and advanceToNextLevel for improved clarity hence, reducing the complexity of the checkIfGameOver method.
* userHasReachedKillTarget() encapsulates the logic for checking whether the user has reached the kill target.
* addUserToScene() adds the player to the root container.
* spawnEnemiesBasedOnProbability() handles the logic of spawning enemies and simplifies the logic inside spawnEnemyUnits().
* spawnSingleEnemy() handles the spawning of a single enemy.
* Adjusted spawning y level for enemy.


# Level Parent
* add User.destroy() to fix the memory usage issues.
* change movement key
* Added Pause/Resume Functionality
* Added Kill Counter
* Modify LevelParent to Use CollisionManager and replace the in-class collision logic with calls to CollisionManager.
* Game loop lifecycle has been moved to GameLoop.
* Collision detection for penetration been moved to CollisionManager.
* added updateScene() tasks that delegates task to managers.
* Moved logic for responding to key presses (W, S, SPACE, and now P) has been moved to the InputHandler class.
* add new font for kill counter


# Level Two
* added healthbar that changed color depending on how many HP left.
* call for shield.
* Removed levelView field as it's redundant since instantiateLevelView() already initializes and returns the view.


# Level View
* adjusted loss, win and hearts display screen x and y positions to center those images.

# Level View Two
* remove a lot of redundant stuff

# Projectile
* Added encapsulated destruction logic in handleDestruction.
* Simplified the constructor by setting a default image height.

# User Plane
* adjust upper_bound
* adjust projectile x and y position.

# User Projectile
- change images and image size

# Win Image
* Added overload constructor 

  
### NEW CLASSES ###

# Game Loop
* Manage the timeline (Timeline) and invoke a provided Runnable to update the game state at regular intervals.
* Provide controls to start, stop, and pause the game.

# Collision Manager
* Handle all collision detection and responses.
* Handle plane collisions.
* Handle projectile collisions (user vs. enemies and enemies vs. user).
* Detect and manage any out-of-bound or penetrated enemies (e.g., enemies reaching the player's side).

# Input Handler
* Handling key input directly.

# Level Semi
* new level thats similar with levelOne but with waves this time which will spawn more enemy after each waves before can go to boss battle.
* added total wave on the screen.

# Main Menu
* Start button
* Select Level Button
* Exit Button
* adjust image resolution and transition.
* add background music function call.
* adjusting button position and transition from one page to another

# Background Music
* handle background music.

# Problems
* The collision hitboxes do not align with the displayed graphics. (solved by Updated graphics and refined hitboxes to correspond with the actor's visual boundaries.)
* Encountered a java.lang.reflect.InvocationTargetException error when moving to Level 2. (solved by Fixed the shield image path by replacing "shield.jpg" with "shield.png".)
* Shield not visible. (solved by declaring in boss and level two.)
