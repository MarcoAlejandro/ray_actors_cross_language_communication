### Testing communication between cross-lang ray actors

This repo contains experiments to determine possible ways to implement collaboration between 
cross language actors inside the ray runtime. 

**Folder structure**

- `scala_ray`: Scala project to be packaged in a `.jar`. 
-  `python`: python modules contaning Java actor calls. 

### Passing around actor handles

According to [documentation](https://docs.ray.io/en/master/cross-language.html#cross-language-data-serialization), `ActorHandle`'s can be serialized and deserialized autatically
between cross language operations. 

Given so, it should be possible to pass the actor handle to remote cross language tasks/actor methods
and to use the actor handle natively in the remote code. 

The `main.py` code performs the next actions: 

- Instantiate a `RayEcho` actor in a worker process. 

- Instantiate some `EchoClient` java actors in worker processses. 

- Call the `addEcho` method on each `EchoClient` remote java actor, and pass the `RayEcho` python handle to those. 

- The `EchoClient` java actors will use the python handle to call the `RayEcho.save()` method.

- When the `EchoClient` calls are completed, it's expected to see the echoes (messages) added in the internal state of the `RayEcho` actor instance. 

Insepcting the output of `main.py`, one could see that the echoes are being hold by the `RayEcho` python actor. And those echoes were added by the Java workers using the 
`RayEcho` python handle: 

```
# output python main.py
>>> $ python main.py

2021-04-16 12:29:59,495 INFO services.py:1172 -- View the Ray dashboard at http://127.0.0.1:8265
['hello world 1', 'hello world 0', 'hello world 2', 'hello world 3']

```

Running the example on a cluster, using the Docker image and provied cluster: 

```
>>> $ kubectl -n ray exec example-cluster-ray-head-tkbdd -- python main.py

2021-04-16 13:29:53,081 INFO worker.py:655 -- Connecting to existing Ray cluster at address: 10.244.1.221:6379
['hello world 1', 'hello world 0', 'hello world 2', 'hello world 3']

```