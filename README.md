  **GITHUB LINK**
  https://github.com/SyukranSJ/MDSCW24

  # CHANGES
  
><><><><><><><><><><><><>><<><><><><><><><><><>

# ShieldImage Class
* correctly updated from "shield.jpg" to "shield.png" to reference the shield image, using the IMAGE_NAME variable with the getResource method.

# Controller

# Main

# ActiveActor

# ActiveActor Destructible

# Boss
* correctly declaring and structured the shield image to make it visible and fully functional.
* added Boss healthbar
* added color that react based on remaining health
* added shield movement 
* move shield declaration from levelviewtwo to boss level.
*

# Boss Projectile

# Destructible

# Enemy Plane

# Enemy Projectile

# FighterPlane

# GameOver Image

# Heart Display



# Level One


# Level Parent
* disabled the user.takeDamage(); under the handleEnemyPenetration() method.
* add User.destroy() to fix the memory usage issues.
* change movement key

# Level Two
* added healthbar that changed color depending on how many HP left.
* call for shield.


# Level View

# Level View Two
* remove a lot of redundant stuff

# Projectile

# User Plane

# User Projectile

# Win Image













# Problems
* The collision hitboxes do not align with the displayed graphics. (solved by Updated graphics and refined hitboxes to correspond with the actor's visual boundaries.)
* The player's health decreases every time an enemy passes through, which may indicate an issue with the collision detection or health deduction logic.(solved by removing solve by removing user.takedamage() under handleEnemyPenetration() method in levelParent)
* Encountered a java.lang.reflect.InvocationTargetException error when moving to Level 2. (solved by Fixed the shield image path by replacing "shield.jpg" with "shield.png".)
* Shield not visible. (solved by declaring in boss and level two.)

  

  
