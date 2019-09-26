import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversion.csv', dtype={'version' : 'str'})

plt.bar(data['version'], data['count'])
plt.xticks(rotation='vertical')
plt.xlabel('JMH Version')
plt.ylabel('Number of projects')
plt.tight_layout()
plt.show()
#plt.savefig('export.png')