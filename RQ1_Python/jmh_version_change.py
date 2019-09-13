import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionchange.csv')

x = data['benchmarks']
y = data['averageChangeTime']

plt.scatter(x,y)
#plt.yscale('log')
#plt.xscale('log')
plt.xlabel('Total number of benchmarks per project')
plt.ylabel('Average time between a version change [years]')
#plt.show()
plt.savefig("export.png")