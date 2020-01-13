import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\javaversion.csv', dtype={'version' : 'str'})

plt.bar(data['version'], data['countShortLived'], label="short-lived projects",  color="green", bottom=data['countLongLived'])
plt.bar(data['version'], data['countLongLived'], label="long-lived projects", color="orange")
plt.xlabel('Java version')
plt.ylabel('# projects')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\javaTarget_version.pdf')