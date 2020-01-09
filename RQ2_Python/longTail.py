import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter
from pylab import rcParams

rcParams['figure.figsize'] = 6.4, 2.5

data = pd.read_csv('D:\\rq2\\results\\csv-log4j2\\log4j2#org.apache.logging.log4j.perf.jmh.FileAppenderBenchmark.logbackAsyncFile#.csv', delimiter=";")

hist = []

for index, row in data.iterrows():
    if row['fork'] == 1:
        for _ in range(int(row['value_count'])):
            hist.append(row['value'])

plt.hist(hist, bins=50, range=[0, 0.000002], weights=np.ones(len(hist)) / len(hist))
plt.gca().yaxis.set_major_formatter(PercentFormatter(1,0))
plt.xlabel('incovation time [ns]')
plt.xticks([0, 0.0000005, 0.000001, 0.0000015, 0.000002], [0, 500, 1000, 1500, 2000])
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')