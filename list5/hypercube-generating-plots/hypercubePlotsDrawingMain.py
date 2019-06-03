import itertools
from collections import defaultdict
import statistics as stat
from pprint import pprint

import matplotlib.pyplot as plt
import numpy as np
import csv

statistics = defaultdict(list)
column_names = ["size", "max flow", "augmenting paths", "time"]
for number in range(1, 17):
    filename = f"data/hybercube{number}.txt"
    with open(filename) as file:
        reader = csv.DictReader(file, column_names, delimiter=";")
        for line in reader:
            statistics[number].append(line)

# grouped = itertools.groupby(statistics.values(), lambda x: x[])
# for name, val in statistics.items():
# grouped.append(itertools.groupby(val, lambda x: x["data_size"]))

# a = [(1, 1), (2, 3), (4, 5), (1, 6), (2, 6)]
# s2 = itertools.groupby(sorted(a, key=lambda x: x[0]), lambda x: x[0])
# s3 = []
# for pair in s2:
#     s3.append((pair[0], sum(map(lambda p: p[1], pair[1]))))
# it = (p[1] for p in pair[1])
# s3.append((pair[0], sum(it)))

# b2 = {xd: [] for xd in statistics.keys()}
# for alg, coll_of_list in statistics.items():
#     b = itertools.groupby(coll_of_list, lambda x: x['data_size'])
#     for size, it_of_dict in b:
#         list_of_dict = list(it_of_dict)
#         b2[alg].append((size, {xD: stat.mean(int(i[xD]) for i in list_of_dict) for xD in column_names[-3:]}))

rng = np.arange(1, 17)

# max flow
tmp = list()
for i in statistics.keys():
    tmp.append(np.mean([float(a['max flow']) for a in statistics[i]]))
plt.plot(rng, tmp)

plt.title("max flow")
plt.xlabel("size")
plt.ylabel("average max flow")
plt.savefig("maxflow.png")
plt.show()

# augmenting paths
tmp = list()
for i in statistics.keys():
    tmp.append(np.mean([float(a['augmenting paths']) for a in statistics[i]]))
plt.plot(rng, tmp)

plt.title("augmenting paths")
plt.xlabel("size")
plt.ylabel("average number of augmenting paths")
plt.savefig("augmenting-paths.png")
plt.show()

# time
tmp = list()
for i in statistics.keys():
    tmp.append(np.mean([float(a['time']) for a in statistics[i]]))
plt.plot(rng, tmp)

plt.title("time")
plt.xlabel("size")
plt.ylabel("average time in milliseconds")
plt.savefig("time.png")
plt.show()


def draw_log_plots():
    # max flow semilogy
    tmp = list()
    for i in statistics.keys():
        tmp.append(np.mean([float(a['max flow']) for a in statistics[i]]))
    plt.semilogy(rng, tmp)

    plt.title("max flow logarithmic")
    plt.xlabel("size")
    plt.ylabel("average max flow")
    plt.savefig("maxflow-logarithmic.png")
    plt.show()

    # augmenting paths semilogy
    tmp = list()
    for i in statistics.keys():
        tmp.append(np.mean([float(a['augmenting paths']) for a in statistics[i]]))
    plt.semilogy(rng, tmp)

    plt.title("augmenting paths logarithmic")
    plt.xlabel("size")
    plt.ylabel("average number of augmenting paths")
    plt.savefig("augmenting-paths-logarithmic.png")
    plt.show()

    # time semilogy
    tmp = list()
    for i in statistics.keys():
        tmp.append(np.mean([float(a['time']) for a in statistics[i]]))
    plt.semilogy(rng, tmp)

    plt.title("time logarithmic")
    plt.xlabel("size")
    plt.ylabel("average time in milliseconds")
    plt.savefig("time-logarithmic.png")
    plt.show()
