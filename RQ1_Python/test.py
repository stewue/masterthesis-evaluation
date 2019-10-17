import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import scipy.stats as st
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('D:\\projects\\apache#logging-log4j2\\log4j-perf\\target\\out-log4j2-5.csv', delimiter=";")
#data = pd.read_csv('D:\\replication_package\\data\\cleaned\\data_bluemix.csv')

# filtered = data[data['iteration'] == 1]
#
# values, base = np.histogram(filtered['value'], bins=1000, range=[0,257], weights=filtered['value_count'])
# cumulative = np.cumsum(values)
# plt.plot(base[:-1], cumulative)
#
# #plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
# plt.tight_layout()
# plt.show()

def weighted_std(values, weights):
    average = np.average(values, weights=weights)
    variance = np.average((values-average)**2, weights=weights)
    return np.sqrt(variance)

mean = np.average(data['value'], weights=data['value_count'])
std = weighted_std(data['value'], data['value_count'])
print("mean: " + str(mean))
print("std: " + str(std))
print("cov: " + str(std / mean))

# filtered = data[data['bench_name'] == 'log4j2-5']
# print(np.average(filtered['value']))
# print(np.std(filtered['value']))
# print(np.std(filtered['value']) / np.average(filtered['value']))