import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

dataChanged = pd.read_csv('D:\\outputPdfChange.csv', delimiter=';')
#dataChanged = pd.read_csv('D:\\outputCiChange.csv', delimiter=';')
#dataChanged = pd.read_csv('D:\\outputCovChange.csv', delimiter=';')

res = [0] * 101
threshold = 0.97
# threshold = 0.03
# threshold = 0.01

for x in range(6, 101):
# for x in range(5, 101):
    counter = 0
    for index, row in dataChanged.iterrows():
        #if row["i" + str(x)] < threshold:
        if row["i" + str(x)] > threshold:
            counter = counter + 1
    res[x] = counter / dataChanged.shape[0]

plt.plot(res, label="in current iteration threshold reached")

dataReached = pd.read_csv('D:\\outputPdfReached.csv', delimiter=';')
#dataReached = pd.read_csv('D:\\outputCiReached.csv', delimiter=';')
#dataReached = pd.read_csv('D:\\outputCovReached.csv', delimiter=';')
allReached = dataReached['reached']

valuesAllReached, baseReached = np.histogram(allReached, bins=100, range=[0, 100], weights=np.ones(len(allReached)) / len(allReached))
cumulativeAllReached = np.cumsum(valuesAllReached)
plt.plot(baseReached[:-1], cumulativeAllReached, label="first time threshold reached")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('# iterations')
plt.ylabel('cumulative probability')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig("export.png")