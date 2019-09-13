import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionage.csv')

filtered = data[data['useJmhSince'] >= 1]
x = filtered['benchmarks']
y = filtered['time']

plt.scatter(x,y)
#plt.yscale('log')
#plt.xscale('log')
plt.xlabel('Total number of benchmarks per project')
plt.ylabel('Age of current used JMH version [years]')
#plt.show()
plt.savefig("export.png")