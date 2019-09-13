import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\featuresperproject.csv', index_col=0)
x = data['numberOfBenchmarks']
y = data['avgNumberOfBenchmarksPerClass']

plt.scatter(x,y)
#plt.yscale('log')
#plt.xscale('log')
plt.ylim(1, 85)
plt.xlim(1, 525)
plt.xlabel('Total number of benchmarks per project')
plt.ylabel('Average number of benchmarks per class')
plt.show()
#plt.savefig('export.png')