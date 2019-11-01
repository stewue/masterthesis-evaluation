import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('D:\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\pdf_difference.csv', delimiter=';')

filtered10 = data[np.logical_not(np.isnan(data['i10']))]
all10 = filtered10['i10']

valuesAll10, base10 = np.histogram(all10, bins=1000, range=[0,1], weights=np.ones(len(all10)) / len(all10))
cumulativeAll10 = np.cumsum(valuesAll10)
plt.plot(base10[:-1], cumulativeAll10, label="10 iterations à 10 seconds")

filtered100 = data[np.logical_not(np.isnan(data['i100']))]
all100 = filtered100['i100']

valuesAll100, base100 = np.histogram(all100, bins=1000, range=[0,1], weights=np.ones(len(all100)) / len(all100))
cumulativeAll100 = np.cumsum(valuesAll100)
plt.plot(base100[:-1], cumulativeAll100, label="100 iterations à 1 second")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('p-value')
plt.ylabel('cumulative probability')
plt.xlim(0.95, 1)
#plt.xscale('log')
#plt.yscale('log')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig("export.png")