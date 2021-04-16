import ray
import time
from ray_echo import RayEcho

# Crates a RayEcho in a worker process.
ray.init(address="auto", job_config=ray.job_config.JobConfig(code_search_path=["echo.jar"]))
echo_actor = RayEcho.remote()

# instantiate java actors
java_echo_class = ray.java_actor_class("org.example.application.EchoClient")
java_handles = [java_echo_class.remote(i) for i in range(0, 4)]

java_echo_refs = [java_handle.addEcho.remote(echo_actor) for java_handle in java_handles]

java_echo_results = [ ray.get(java_echo_ref) for java_echo_ref in java_echo_refs]

time.sleep(2)

echoes = ray.get(echo_actor.get_echoes.remote())

assert len(echoes) == 4

print(echoes)
