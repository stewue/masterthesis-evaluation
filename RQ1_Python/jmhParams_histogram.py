import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('D:\\mp\\current-merged-isMain-header.csv')

filtered = data[data['jmhParamCount'] > 0]
values = filtered['jmhParamCount']
valuesAll, base = np.histogram(values, bins=7, range=[1,7], weights=np.ones(len(values)) / len(values))
cumulativeAll = np.cumsum(valuesAll)
x = np.arange(1, 8)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.bar(x, valuesAll)
plt.xlabel('numberOfJmhParams')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')