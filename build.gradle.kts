import java.io.File

plugins {
    id("com.palantir.docker") version "0.36.0"
}

tasks {
    register("downContainers") {
        group = "Docker"
        description = "Stops and removes Docker Compose containers, networks, and volumes"
        doLast {
            exec {
                commandLine = listOf("docker-compose", "down")
            }
        }
    }

    register("stopContainers") {
        group = "Docker"
        description = "Stops Docker Compose containers"
        doLast {
            exec {
                commandLine = listOf("docker-compose", "stop")
            }
        }
    }

    register("removeNetwork") {
        group = "Docker"
        description = "Removes the spring-cloud-microservices network if it exists"
        doLast {
            exec {
                commandLine = listOf("docker", "network", "rm", "spring-cloud-microservices")
                // Ігноруємо помилку, якщо мережі немає
                isIgnoreExitValue = true
            }
        }
    }


    register("createNetwork") {
        group = "Docker"
        description = "Creates the spring-cloud-microservices network"
        doLast {
            exec {
                commandLine = listOf("docker", "network", "create", "spring-cloud-microservices")
            }
        }
    }


    register("startServices") {
        group = "Docker"
        description = "Starts all services with docker-compose"
        doLast {
            println("Building and starting services...")
            exec {
                environment("COMPOSE_HTTP_TIMEOUT", "600")
                commandLine = listOf("docker-compose", "up", "--build", "-d")
                standardOutput = System.out
                errorOutput = System.err
            }
            println("Services started. Streaming logs (press Ctrl+C to stop)...")
            exec {
                commandLine = listOf("docker-compose", "logs", "--follow")
                standardOutput = System.out
                errorOutput = System.err
            }
        }
    }

    register("showLogs") {
        group = "Docker"
        description = "Shows logs for all running services"
        doLast {
            println("Fetching logs...")
            exec {
                commandLine = listOf("docker-compose", "logs")
                standardOutput = System.out
                errorOutput = System.err
            }
        }
    }

    register("deploy") {
        group = "Docker"
        description = "Full deployment process for Spring Cloud microservices"
        dependsOn("stopContainers", "removeNetwork", "createNetwork", "startServices")
        finalizedBy("showLogs")
    }



}

tasks.named("removeNetwork") {
    mustRunAfter("stopContainers")
}

tasks.named("createNetwork") {
    mustRunAfter("removeNetwork")
}

/*
tasks.named("startServices") {
    mustRunAfter("createNetwork")
}*/
