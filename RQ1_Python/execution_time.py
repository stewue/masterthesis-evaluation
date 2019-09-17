import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
x = data['measurementTimePercentage']
y = data['warmupTimePercentage']

plt.scatter(x,y)
plt.ylim(0, 1.25)
plt.xlim(0, 1.25)
plt.xlabel('measurementTimePercentage')
plt.ylabel('warmupTimePercentage')
#plt.show()
plt.savefig('export.png')