The particle gravity simulation project in Java Swing is an implementation of a model that simulates the movement of particles under the influence of gravitational force. Two solutions are implemented within the project: "brute force" and "quad tree".

The "brute force" solution is a simple approach to simulating gravity that relies on the direct calculation of forces between each particle and every other particle in the simulation. This method is quite easy to implement, but has a quadratic complexity, which means that for each particle in the simulation, the presence of every other particle must be calculated, which can become very slow as the number of particles in the simulation increases.

The second solution used in this project is the "quad tree" algorithm. This algorithm is used to optimize the simulation by reducing the number of force calculations between particles. The quad tree is a tree consisting of four quadrants that are divided into sub-quadrants and so on until each square can contain only one particle. This algorithm allows to avoid the calculation of forces between particles that are far away from each other, resulting in a faster simulation.

In the implementation of the project, Java Swing is used to display the simulation on the screen, and the user can set various parameters such as the number of particles, speed, size, and initial position of the particles. When the simulation is started, the user can see how the particles move under the influence of gravitational forces and how their position changes over time.


https://user-images.githubusercontent.com/93669392/231096205-3a678803-234d-49f8-afca-e889e80b9017.mp4

