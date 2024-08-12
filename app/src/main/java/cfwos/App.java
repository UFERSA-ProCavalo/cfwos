package cfwos;

import cfwos.server.Server;
import cfwos.client.Client;
import java.util.Scanner;

public class App {

        public static void main(String[] args) {
                System.out.println("\033[2J\033[1;1H");

                System.out.println("Welcome to the Cosmic Fleet Work Order System!");

                Scanner scanner = new Scanner(System.in);
                Server server = new Server();

                System.out.println("Server initialized.");

                // Add some initial work orders to the server
                // {
                // server.getDatabase().addWorkOrder(1, "Galaxy Patrol",
                // "Routine inspection of the Andromeda sector.");
                // server.getDatabase().addWorkOrder(2, "Starship Refit",
                // "Upgrade systems for deep space exploration.");
                // server.getDatabase().addWorkOrder(3, "Nebula Cleanup", "Remove debris from
                // the Orion Nebula.");
                // server.getDatabase().addWorkOrder(4, "Black Hole Study", "Gather data from
                // the event horizon.");
                // server.getDatabase().addWorkOrder(5, "Alien Artifact",
                // "Secure and analyze unidentified artifact.");
                // server.getDatabase().addWorkOrder(6, "Comet Tracking",
                // "Monitor and document the Halley’s Comet pass.");
                // server.getDatabase().addWorkOrder(7, "Space Station Docking",
                // "Assist in the docking of the ISS.");
                // server.getDatabase().addWorkOrder(8, "Martian Colony",
                // "Supply and support for the Mars settlement.");
                // server.getDatabase().addWorkOrder(9, "Asteroid Mining",
                // "Extract valuable minerals from asteroid belt.");
                // server.getDatabase().addWorkOrder(10, "Interstellar Communication",
                // "Repair and test the long-range transmitter.");
                // server.getDatabase().addWorkOrder(11, "Quantum Research",
                // "Experiment with quantum entanglement phenomena.");
                // server.getDatabase().addWorkOrder(12, "Solar Flare Defense",
                // "Implement shielding against solar flare emissions.");
                // server.getDatabase().addWorkOrder(13, "Lunar Base Expansion",
                // "Construct additional modules for the Moon base.");
                // server.getDatabase().addWorkOrder(14, "Extraterrestrial Life",
                // "Research potential signals from distant galaxies.");
                // server.getDatabase().addWorkOrder(15, "Gravity Well Simulation",
                // "Run simulations for new gravity models.");
                // server.getDatabase().addWorkOrder(16, "Warp Drive Calibration",
                // "Calibrate the warp drive for faster-than-light travel.");
                // server.getDatabase().addWorkOrder(17, "Cosmic Ray Detection",
                // "Install sensors to detect and analyze cosmic rays.");
                // server.getDatabase().addWorkOrder(18, "Star Cluster Mapping",
                // "Create detailed maps of the Pleiades star cluster.");
                // server.getDatabase().addWorkOrder(19, "Exoplanet Survey",
                // "Survey potential habitable exoplanets.");
                // server.getDatabase().addWorkOrder(20, "Alien Language",
                // "Decrypt and study newly discovered alien language.");
                // server.getDatabase().addWorkOrder(21, "Astrobiology",
                // "Conduct experiments to discover life in extreme conditions.");
                // server.getDatabase().addWorkOrder(22, "Nebula Photography",
                // "Capture high-resolution images of the Eagle Nebula.");
                // server.getDatabase().addWorkOrder(23, "Space-time Anomaly",
                // "Investigate unusual space-time distortions.");
                // server.getDatabase().addWorkOrder(24, "Galactic Census",
                // "Compile data on the population of the Milky Way.");
                // server.getDatabase().addWorkOrder(25, "Supernova Watch",
                // "Monitor for supernovae in nearby galaxies.");
                // server.getDatabase().addWorkOrder(26, "Asteroid Defense System",
                // "Develop and test an asteroid deflection system.");
                // server.getDatabase().addWorkOrder(27, "Intergalactic Trade",
                // "Facilitate trade agreements with Andromeda representatives.");
                // server.getDatabase().addWorkOrder(28, "Alien Ecosystem",
                // "Study the ecosystem of an alien planet.");
                // server.getDatabase().addWorkOrder(29, "Cosmic Dust Collection",
                // "Collect and analyze cosmic dust samples.");
                // server.getDatabase().addWorkOrder(30, "Galactic Mapping",
                // "Update the galactic map with recent discoveries.");
                // server.getDatabase().addWorkOrder(31, "Space Elevator Maintenance",
                // "Perform maintenance on the orbital space elevator.");
                // server.getDatabase().addWorkOrder(32, "Astronomical Event",
                // "Prepare for and observe the upcoming astronomical event.");
                // server.getDatabase().addWorkOrder(33, "Energy Shield Test",
                // "Test new energy shielding technology for space vehicles.");
                // server.getDatabase().addWorkOrder(34, "Planetary Atmosphere",
                // "Study the atmospheric conditions of newly discovered planets.");
                // server.getDatabase().addWorkOrder(35, "Star Engine Prototype",
                // "Develop and test the prototype for a new star engine.");
                // server.getDatabase().addWorkOrder(36, "Temporal Anomaly",
                // "Investigate a detected temporal anomaly in the space-time continuum.");
                // server.getDatabase().addWorkOrder(37, "Interstellar Probe",
                // "Launch and monitor an interstellar probe.");
                // server.getDatabase().addWorkOrder(38, "Celestial Navigation",
                // "Update navigation charts for new celestial bodies.");
                // server.getDatabase().addWorkOrder(39, "Astroengineering",
                // "Design and construct new space structures.");
                // server.getDatabase().addWorkOrder(40, "Lunar Soil Sample",
                // "Collect and analyze samples from the lunar surface.");
                // server.getDatabase().addWorkOrder(41, "Cosmic Signal Detection",
                // "Enhance equipment for detecting faint cosmic signals.");
                // server.getDatabase().addWorkOrder(42, "Space Colony Design",
                // "Develop plans for a new space colony.");
                // server.getDatabase().addWorkOrder(43, "Extraterrestrial Artifacts",
                // "Catalog and study artifacts from alien civilizations.");
                // server.getDatabase().addWorkOrder(44, "Galaxy Exploration",
                // "Prepare for a mission to explore an uncharted galaxy.");
                // server.getDatabase().addWorkOrder(45, "Space Weather Monitoring",
                // "Monitor and predict space weather conditions.");
                // server.getDatabase().addWorkOrder(46, "Astronomical Observatory",
                // "Upgrade facilities at the deep space observatory.");
                // server.getDatabase().addWorkOrder(47, "Wormhole Stability",
                // "Research the stability of newly discovered wormholes.");
                // server.getDatabase().addWorkOrder(48, "Star Forge Research",
                // "Investigate processes related to star formation.");
                // server.getDatabase().addWorkOrder(49, "Dark Matter Study",
                // "Conduct experiments to understand dark matter properties.");
                // server.getDatabase().addWorkOrder(50, "Space-time Travel",
                // "Test new theories for space-time travel.");
                // server.getDatabase().addWorkOrder(51, "Galactic Federation",
                // "Organize and coordinate with the Galactic Federation.");
                // server.getDatabase().addWorkOrder(52, "Asteroid Belt Survey",
                // "Survey and catalog objects in the asteroid belt.");
                // server.getDatabase().addWorkOrder(53, "Space Station Upgrade",
                // "Implement upgrades to the main space station.");
                // server.getDatabase().addWorkOrder(54, "Gravity Field Analysis",
                // "Analyze and document new gravity fields.");
                // server.getDatabase().addWorkOrder(55, "Quantum Computing",
                // "Test quantum computing advancements in space applications.");
                // server.getDatabase().addWorkOrder(56, "Solar System Mapping",
                // "Update the map of our solar system with recent data.");
                // server.getDatabase().addWorkOrder(57, "Star Observation",
                // "Conduct detailed observations of newly discovered stars.");
                // server.getDatabase().addWorkOrder(58, "Nebula Research",
                // "Explore and study the structure of various nebulae.");
                // server.getDatabase().addWorkOrder(59, "Planetary Defense",
                // "Enhance planetary defense systems against potential threats.");
                // server.getDatabase().addWorkOrder(60, "Space-time Experiment",
                // "Conduct an experiment to test space-time distortions.");
                // }
                // Show server work orders
                System.out.println("Initial Work Orders in the Server:");
                server.getDatabase().showDatabase();

                // Initialize the client
                Client client = new Client(scanner, server, "Client Joaquim Barbosa");
                System.out.println("\nClient initialized. Ready to interact with the server.");

                // Interact with the server through the client

                boolean running = true;
                while (running) {
                        System.out.println("\n");
                        System.out.println("Client Menu:");
                        System.out.println("[1]. Insert WorkOrder");
                        System.out.println("[2]. Remove WorkOrder");
                        System.out.println("[3]. Update WorkOrder");
                        System.out.println("[4]. Search WorkOrder");
                        System.out.println("[5]. Show all WorkOrders");
                        System.out.println("[6]. Exit");
                        System.out.print("Choose an option: ");

                        char choice = scanner.next().charAt(0);
                        System.out.println("\033[2J\033[1;1H");
                        switch (choice) {
                                case '1':
                                        client.insertWorkOrder();
                                        break;
                                case '2':
                                        client.removeWorkOrder();
                                        break;
                                case '3':
                                        client.updateWorkOrder();
                                        break;
                                case '4':
                                        client.searchWorkOrder();
                                        break;
                                case '5':
                                        client.listWorkOrders();
                                        break;
                                case '6':
                                        running = false;
                                        break;
                                case '7':
                                        System.err.println(server.getDatabase().getBalanceCounter());
                                        break;
                                case '8':
                                        client.getCache().showCache();
                                        break;
                                default:
                                        System.out.println("Operation not listed, please try again.");
                                        break;
                        }
                }

                System.out.println("Program terminated.");
                scanner.close();
        }
}