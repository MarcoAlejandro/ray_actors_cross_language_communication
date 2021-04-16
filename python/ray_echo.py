import ray


@ray.remote
class RayEcho(object):
    def __init__(self):
        self.echoes = []

    def get_echoes(self):
        return self.echoes

    def save(self, message: str) -> int:
        self.echoes.append(message)
        return 0
