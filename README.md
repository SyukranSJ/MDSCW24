  **GITHUB LINK**
  https://github.com/SyukranSJ/MDSCW24

  # CHANGES
  
><><><><><><><><><><><><>><<><><><><><><><><><>

# ShieldImage Class
* correctly updated from "shield.jpg" to "shield.png" to reference the shield image, using the IMAGE_NAME variable with the getResource method.







# Problems
* The collision hitboxes do not align with the displayed graphics. (solved by Updated graphics and refined hitboxes to correspond with the actor's visual boundaries.)
* The player's health decreases every time an enemy passes through, which may indicate an issue with the collision detection or health deduction logic.(solved by removing solve by removing user.takedamage() under handleEnemyPenetration() method in levelParent)
* Encountered a java.lang.reflect.InvocationTargetException error when moving to Level 2. (solved by Fixed the shield image path by replacing "shield.jpg" with "shield.png".)
* 

  

  
