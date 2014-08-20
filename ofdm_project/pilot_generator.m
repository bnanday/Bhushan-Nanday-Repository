function[pilot,k]=pilot_generator(l,ns)

Ncp=1; %Normal cyclic prefix

N_cell_id=0; % Assuming cell id to be zero

k1 = [];
k=[];
pilot = [];

%Cinit is used to initialize the second m sequence
Cinit=(2^10)*(7*(ns+1)+l+1)*(2*N_cell_id+1)+ 2*N_cell_id + Ncp; 

Cinit_bin=dec2bin(Cinit,31); %Converting the decimal value of Cinit to binary

x1=[1,zeros(1,30)];

x2=Cinit_bin;

c=zeros(1,441);

for n=1:1:2041
    
    msb_x1 = mod(x1(1)+ x1(4),2);
    msb_x2 = mod(x2(1)+ x2(2)+ x2(3)+ x2(4),2);
    
    x1=[x1(2:length(x1)), msb_x1]; 
    x2=[x2(2:length(x2)), msb_x2];
    
    c(n) = mod(x1(1)+x2(1),2);
    
end

new_c =  c(1601 : 2041);



for m=1:1:220
    
    r(m)=1/sqrt(2)*(1-2*new_c(2*m))+1j*1/sqrt(2)*(1-2*new_c(2*m+1));
    
end

vshift=mod(N_cell_id,6);
    v=[];
    for m=0:49
        m1=m+85;
       if (l==0)
           v=0;
       else if(l==4)   
           v=3;
           end
       end
       k1=6*m+mod((v+vshift),6);
       k=[k k1+1];
       a=r(m1+1);
       pilot=[pilot a];
    end 
    
    
end


