import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('D:\\rq2\\pre\\mean.csv', delimiter=";")
percentage = data['percentage']

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.hist(percentage, range=[0.75,1.25], bins=50, weights=np.ones(len(percentage)) / len(percentage))
plt.ylabel('# benchmarks')
plt.xlabel('change rate')
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')

allCount = len(data)
less1Per = percentage[abs(percentage - 1) < 0.01].size
greater5Per = percentage[abs(percentage - 1) > 0.05].size
print("<1%:" + str(less1Per/allCount))
print(">5%:" + str(greater5Per/allCount))
print("avg:" + str(np.average(percentage)-1))
