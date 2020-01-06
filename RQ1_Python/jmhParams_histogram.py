import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('D:\\mp\\current-merged-isMain-header.csv')

filtered = data[data['jmhParamCount'] > 0]
values = filtered['jmhParamCount']
valuesAll, base = np.histogram(values, bins=[1,2,3,4,5,6,7,10], weights=np.ones(len(values)) / len(values))
cumulativeAll = np.cumsum(valuesAll)
x = ["1", "2", "3", "4", "5", "6", "7-10"]

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.bar(x, valuesAll)
plt.ylabel("probability")
plt.xlabel('# JMH parameters')
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')