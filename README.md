# What is Zodiac?
Zodiac is an in progress real time  grand strategy game that encompasses land and space combat as well as everything in between.

I noticed that very few games merge land and space combat and that there are many interesting oppurtunities in doing so. The end goal is to have a full space to land siege realized and for the player to experience the chaotic brutality at every single state: The initial space battle. Taking over the planet's orbit. Fighting in the atmosphere. Deploying troops onto the surface. Enclosing on the enemy installations and finally crushing their capital city.

#Gameplay Concepts
Because managing both land and space is both a gameplay and UI challenge, I've decided to split them into two entirely seperate views, perhaps making them both visible at the same time during a later date. By default, the 'space' key toggles between them.

After a certain zoom height, icons take the shape of units so that the player can keep tabs on them even when zoomed out to the maximum. This applies to both land and space structures and units.

Units behave very autonomously and do not have to be consistently microed. The player instead sets them to a behavior (defensive, offensive, standard) and gives them an objective. The units will then move to the target area and engage enemies as the behavior dictates. Defensive means they will remain at maximum range whereas offensive ensures they close in to maximum effective range as quickly as possible. Currently trying to get units to behave well in a group.

Space fighters, corvettes and certain frigates can descend into the atmosphere (the land plane) to provide support. This greatly increases their chance of getting shot down but also makes them much more effective in a clsoe fire support role. When ordered into the atmosphere, space fighters will engage in several strafing runs before returning to space (or can be ordered to abort at any time). Frigates and corvettes will stay until you tell them to return to space.

During larger scale campaign missions, much of your fleet will be AI controlled to avoid the player from being overwhelmed. Currently I am experimenting with having the player give high level orders to AI allied fleets / armies.

Individual missions / battles are planned to take a significant amount of time to maintain an epic feel to them and to feel like the player is progressing through a space siege. You can think of the different stages of an attack / defense as separate missions altogether.

Even though the Z axis will play heavily for air units and units descending / ascending from / into space, all units will be two dimensional. I believe that if the game engine were to be in 3D, the game would reach unacceptable lag and would suffer from a UI standpoint. The game is specifically designed to have a very large number of active entities on the map and to retain dead ones to really give the feeling of battlefield carnage as time goes on.

To maintain a distinct z-axis, a lot of work is being put into the scaling and movement of units in the atmosphere to make them seem as if they are actually changing height.

#Future Plans
A full campaign is planned to give the player a custom tailored fun experience

A galactic war / more open world mode is currently being drafted and may be included somewhere down the line if it makes gameplay sense.
