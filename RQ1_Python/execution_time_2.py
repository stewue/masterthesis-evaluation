import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')

filtered = data[data['executionTimePercentage'] != 1]
x = filtered['executionTimePercentage']

plt.hist(x, bins=100, range=[0,3])
plt.xlabel('executionTimePercentage')
#plt.show()
plt.savefig('export.png')